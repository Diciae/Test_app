package kuzmindenis.test.project.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import kuzmindenis.test.project.data.Offer;
import kuzmindenis.test.project.model.Category;
import kuzmindenis.test.project.model.Product;
import kuzmindenis.test.project.model.Program;
import kuzmindenis.test.project.repository.client.AdmitadRestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class ProgramLongPollingService {

    private static final int DEFAULT_PROGRAMS_LIMIT = 4;

    private final AccessTokenService accessTokenService;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final ProgramService programService;
    private final AdmitadRestClient admitadRestClient;
    private final int longPollingDelayMin;

    private final Executor threadPool = Executors.newFixedThreadPool(1);

    public ProgramLongPollingService(
            AccessTokenService accessTokenService,
            CategoryService categoryService,
            ProductService productService,
            ProgramService programService,
            AdmitadRestClient admitadRestClient,
            @Value("${program-long-polling-service.delay.min}") int longPollingDelayMin
    ) {
        this.accessTokenService = accessTokenService;
        this.categoryService = categoryService;
        this.productService = productService;
        this.programService = programService;
        this.admitadRestClient = admitadRestClient;
        this.longPollingDelayMin = longPollingDelayMin;
    }

    @PostConstruct
    public void start() {
        threadPool.execute(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                savePrograms();
                try {
                    TimeUnit.MINUTES.sleep(longPollingDelayMin);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    private void savePrograms() {
        String accessToken = accessTokenService.getAccessToken();
        JsonNode rawPrograms = admitadRestClient.getPrograms(accessToken, DEFAULT_PROGRAMS_LIMIT);
        ArrayNode programsNodes = (ArrayNode) rawPrograms.get("results");
        for (JsonNode programsNode : programsNodes) {
            long programId = programsNode.get("id").asLong();
            String name = programsNode.get("name").asText();
            String actionsDetail = programsNode.get("actions_detail").toPrettyString();
            String image = programsNode.get("image").asText();
            ArrayNode categoriesNodes = (ArrayNode) programsNode.get("categories");
            String gotolink = programsNode.get("gotolink").asText();
            String productsXmlLink = null;
            if (programsNode.hasNonNull("products_xml_link")) {
                productsXmlLink = programsNode.get("products_xml_link").asText();
            }

            List<Category> categories = new ArrayList<>();
            for (JsonNode categoryNode : categoriesNodes) {
                long id = categoryNode.get("id").asLong();
                String cName = categoryNode.get("name").asText();
                Category category = new Category(
                        id,
                        cName,
                        new byte[0]
                );
                categoryService.saveCategory(category);
                categories.add(category);
            }
            programService.saveProgram(new Program(
                    programId,
                    name,
                    actionsDetail,
                    image,
                    categories,
                    gotolink,
                    productsXmlLink
            ));

            if (productsXmlLink != null) {
                List<Offer> offers;
                try {
                    offers = getOffers(productsXmlLink);
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }

                List<Product> products = new ArrayList<>();
                for (Offer offer : offers) {
                    products.add(new Product(
                            new Product.ProductId(programId, offer.id),
                            offer.name,
                            offer.model,
                            offer.price,
                            offer.url
                    ));
                }
                productService.saveProducts(products);
                System.out.println("Programs and products are successfully fetched");
            } else {
                System.out.println("No products are provided for program: " + programId);
            }

        }
    }

    private List<Offer> getOffers(String productsXmlLink) {
        List<Offer> result = new ArrayList<>();
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
            dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new URL(productsXmlLink).openStream());

            NodeList offerNodeList = doc.getElementsByTagName("offer");
            for (int i = 0; i < offerNodeList.getLength(); i++) {
                Node offerNode = offerNodeList.item(i);
                long id = Long.parseLong(offerNode.getAttributes().getNamedItem("id").getTextContent());
                String name = getTextContentOrNull(((Element) offerNode).getElementsByTagName("name").item(0));
                String model = getTextContentOrNull(((Element) offerNode).getElementsByTagName("model").item(0));
                double price = Double.parseDouble(((Element) offerNode).getElementsByTagName("price").item(0).getTextContent());
                String buyUrl = getTextContentOrNull(((Element) offerNode).getElementsByTagName("url").item(0));

                result.add(new Offer(
                        id,
                        name,
                        model,
                        price,
                        buyUrl
                ));
            }
            return result;

        } catch (ParserConfigurationException | SAXException | IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static String getTextContentOrNull(Node node) {
        if (node == null) {
            return null;
        }
        return node.getTextContent();
    }
}
