package kuzmindenis.test.project.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "products")
public class Product {

    @EmbeddedId
    private ProductId id;

    @Column(name = "name")
    private String name;

    @Column(name = "model")
    private String model;

    @Column(name = "price")
    private Double price;

    @Column(name = "buy_url", length = 1000000000)
    private String buyUrl;

    public Product() {
    }

    public Product(ProductId id, String name, String model, Double price, String buyUrl) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.price = price;
        this.buyUrl = buyUrl;
    }

    /*------------------------------------------*/

    @Embeddable
    public static class ProductId implements Serializable {

        @Column(name = "program_id")
        public Long programId;
        @Column(name = "product_id")
        public Long productId;

        public ProductId() {
        }

        public ProductId(Long programId, Long productId) {
            this.programId = programId;
            this.productId = productId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ProductId productId1 = (ProductId) o;

            if (!programId.equals(productId1.programId)) return false;
            return productId.equals(productId1.productId);
        }

        @Override
        public int hashCode() {
            int result = programId.hashCode();
            result = 31 * result + productId.hashCode();
            return result;
        }
    }

    /* Getters, Setters */

    public ProductId getId() {
        return id;
    }

    public void setId(ProductId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getBuyUrl() {
        return buyUrl;
    }

    public void setBuyUrl(String buyUrl) {
        this.buyUrl = buyUrl;
    }
}