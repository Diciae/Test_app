package restclient.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Category")
public class Category {

    @Id
    @SequenceGenerator(name = "categorySequence", sequenceName = "category_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categorySequence")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "category_name")
    private String name;
    @Column(name = "category_picture")
    private String picture;

    public Category (String name, String brand, Double price) {
        this.name = name;
        this.picture = picture;
    }


    public Category() {

    }
}