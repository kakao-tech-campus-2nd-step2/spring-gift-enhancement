package gift.product.entity;

import gift.category.entity.Category;
import gift.product.dto.ProductRequest;
import gift.wishlist.entity.Wish;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 15)
    private String name;
    @Column(nullable = false)
    private int price;
    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @OneToMany(mappedBy = "product", orphanRemoval = true)
    private final List<Wish> wishList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_name", nullable = false)
    private Category category;

    protected Product() {
    }

    public Product(String name,
                   int price,
                   String imageUrl,
                   Category category) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    protected Product() {
    }

    public Long getId() {
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

    public void update(ProductRequest request, Category category) {
        this.name = request.name();
        this.price = request.price();
        this.imageUrl = request.imageUrl();
        this.category = category;
    }

}
