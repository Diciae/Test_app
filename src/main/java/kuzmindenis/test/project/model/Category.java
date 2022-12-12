package kuzmindenis.test.project.model;

import javax.persistence.*;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "image")
    @Lob
    private byte[] image;


    public Category() {
    }

    public Category(Long id, String name, byte[] image) {
        this.id = id;
        this.name = name;
        this.image = image;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
