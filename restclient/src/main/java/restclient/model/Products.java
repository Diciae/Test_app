
package restclient.model;

        import javax.persistence.*;
        import java.util.ArrayList;
        import java.util.List;

@Entity
@Table(name = "Products")
public class Products {

    @Id
    @SequenceGenerator(name = "productsSequence", sequenceName = "products_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productsSequence")
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(name = "product_name")
    private String productName;
    @Column(name = "product_model")
    private String productModel;
    @Column(name = "product_price")
    private Integer productPrice;
    @Column(name = "product_buy_url")
    private String productBuyUrl;


    public Products(Long id, String productName, String productModel, Integer productPrice, String productBuyUrl) {
        this.id = id;
        this.productName = productName;
        this.productModel = productModel;
        this.productPrice = productPrice;
        this.productBuyUrl = productBuyUrl;
    }

    public Products() {

    }
}