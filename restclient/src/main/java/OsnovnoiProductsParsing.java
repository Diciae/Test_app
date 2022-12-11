
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

    public class OsnovnoiProductsParsing {

        public static void main(String[] args) {

            try {
                // Создается построитель документа
                DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                // Создается дерево DOM документа из файла
                Document document = documentBuilder.parse("osnovnoi_products.xml");

                // Получаем корневой элемент
                Node root = document.getDocumentElement();

                System.out.println("Основные продукты:");
                System.out.println();
                // Просматриваем все подэлементы корневого - т.е. книги
                NodeList offers = root.getChildNodes();
                for (int i = 0; i < offers.getLength(); i++) {
                    Node offer = offers.item(i);
                    // Если нода не текст, то это книга - заходим внутрь
                    if (offer.getNodeType() != Node.TEXT_NODE) {
                        NodeList bookProps = offer.getChildNodes();
                        for(int j = 0; j < bookProps.getLength(); j++) {
                            Node bookProp = bookProps.item(j);
                            // Если нода не текст, то это один из параметров книги - печатаем
                            if (bookProp.getNodeType() == Node.TEXT_NODE) {
                                System.out.println(bookProp.getNodeName() + ":" + bookProp.getChildNodes().item(0).getTextContent());
                            }
                        }
                        System.out.println("===========>>>>");
                    }
                }

            } catch (ParserConfigurationException ex) {
                ex.printStackTrace(System.out);
            } catch (SAXException ex) {
                ex.printStackTrace(System.out);
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }
