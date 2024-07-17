package gift.domain.product;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long price;

    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    protected Product() {}

    public Product(String name, Long price, String description, String imageUrl) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void update(String name, Long price, String description, String imageUrl) {
        if (name != null && !name.isEmpty()) {
            this.name = name;
        }
        if (price != null && price > 0) {
            this.price = price;
        }
        if (description != null && !description.isEmpty()) {
            this.description = description;
        }
        if (imageUrl != null && !imageUrl.isEmpty()) {
            this.imageUrl = imageUrl;
        }
    }

    public void updateImage(String imageUrl) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            this.imageUrl = imageUrl;
        }
    }
}
