package gift.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "category", uniqueConstraints = {
    @UniqueConstraint(name = "uk_category", columnNames = "name")
})
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 7)
    private String color;

    private String description;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @OneToOne(mappedBy = "category")
    private Product product;

    protected Category() {
    }

    public Category(String name, String color, String description, String imageUrl) {
        this.name = name;
        this.color = color;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public Category(String name, String color, String imageUrl) {
        this(name, color, null, imageUrl);
    }

    public Long getId() {
        return id;
    }
}
