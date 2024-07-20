package gift.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
public class Category {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long Id;
    @Column(nullable = false, unique = true, length = 7)
    String name;
    @Column
    String color;
    @Column
    String description;
    @Column(nullable = false)
    String imageUrl;

    public Category(Long id, String name, String color, String description, String imageUrl) {
        Id = id;
        this.name = name;
        this.color = color;
        this.description = description;
        this.imageUrl = imageUrl;
    }
    public Category( String name, String color, String description, String imageUrl) {
       this(null,name,color,description,imageUrl);
    }

    public Long getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
