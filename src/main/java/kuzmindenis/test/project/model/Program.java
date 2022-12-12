package kuzmindenis.test.project.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "programs")
public class Program {

    //название,
    //детали акций (action_details),
    //картинка (image),
    //категории,
    //URL для покупок (gotolink)
    //главный фид продуктов (products_xml_link).
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "action_details", length = 1000000000)
    private String actionDetails;

    @Column(name = "image")
    private String image;

    @OneToMany(targetEntity = Category.class, cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id")
    @Column(name = "categories")
    private List<Category> categories;

    @Column(name = "go_to_link")
    private String goToLink;

    @Column(name = "product_xml_link")
    private String productXmlLink;

    public Program(
            Long id,
            String name,
            String actionDetails,
            String image,
            List<Category> categories,
            String goToLink,
            String productXmlLink
    ) {
        this.id = id;
        this.name = name;
        this.actionDetails = actionDetails;
        this.image = image;
        this.categories = categories;
        this.goToLink = goToLink;
        this.productXmlLink = productXmlLink;
    }

    public Program() {

    }

    /* Getters, Setters */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActionDetails() {
        return actionDetails;
    }

    public void setActionDetails(String actionDetails) {
        this.actionDetails = actionDetails;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getGoToLink() {
        return goToLink;
    }

    public void setGoToLink(String goToLink) {
        this.goToLink = goToLink;
    }

    public String getProductXmlLink() {
        return productXmlLink;
    }

    public void setProductXmlLink(String productXmlLink) {
        this.productXmlLink = productXmlLink;
    }
}