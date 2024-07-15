package gift.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String color;
    private String imageurl;
    private String description;

    public Category() {
    }

    public Category(Long id, String name, String color, String imageurl, String description) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.imageurl = imageurl;
        this.description = description;
    }

    public Category(CategoryDTO categoryDTO) {
        this.name = categoryDTO.getName();
        this.color = categoryDTO.getColor();
        this.imageurl = categoryDTO.getImageurl();
        this.description = categoryDTO.getDescription();
    }

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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
