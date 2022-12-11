package restclient.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Program")
public class Program {

    @Id
    @SequenceGenerator(name = "programSequence", sequenceName = "program_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "programSequence")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "category")
    private List<> category = new ArrayList<>();
    @Column(name = "products")
    private List<> products = new ArrayList<>();
    @Column(name = "action_detales")
    private String actionDetails;
    @Column(name = "go_to_link")
    private String GoToLink;
    @Column(name = "product_xml_link")
    private String ProductXmlLink;


    public Program(Long id, List category, List product, String actionDetails, String goToLink, String productXmlLink) {
        this.id = id;
        this.category = category;
        this.products = product;
        this.actionDetails = actionDetails;
        GoToLink = goToLink;
        ProductXmlLink = productXmlLink;
    }

    public Program() {

    }
}