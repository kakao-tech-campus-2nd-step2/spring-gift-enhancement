package gift.product;

import gift.category.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Optional;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 15)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String imageUrl;

    @JoinColumn(name = "category", nullable = false)
    @ManyToOne
    private Category category;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Category getCategory() {
        return category;
    }

    protected Product() {
    }

    public Product(long id, String name, int price, String imageUrl, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    public Product(ProductDTO productDTO, Category category) {
        this(
            -1L,
            productDTO.name(),
            productDTO.price(),
            productDTO.imageUrl(),
            category
        );
    }

    public void updateProduct(Product product) {
        Optional.ofNullable(product.name)
            .ifPresent(updateName -> this.name = updateName);
        Optional.of(product.price)
            .ifPresent(updatePrice -> this.price = updatePrice);
        Optional.ofNullable(product.imageUrl)
            .ifPresent(updateImageUrl -> this.imageUrl = updateImageUrl);
        Optional.ofNullable(product.category)
            .ifPresent(categoryDTO -> category.updateCategory(category));
    }

    public void updateProduct(ProductDTO productDTO, Category category) {
        updateProduct(new Product(productDTO, category));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Product product) {
            return this.id == product.id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (int) this.id;
    }
}
