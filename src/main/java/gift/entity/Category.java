package gift.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "imgeUrl", nullable = false)
    private String imgUrl;

    @Column(name = "description", nullable = false)
    private String description;

    protected Category() {}

    public Category(String name, String color, String imgUrl, String description) {
        this.name = name;
        this.color = color;
        this.imgUrl = imgUrl;
        this.description = description;
    }

    public Category(int id, String name, String color, String imgUrl, String description) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.imgUrl = imgUrl;
        this.description = description;
    }

    public int getId() {
        return id;
    }
}
