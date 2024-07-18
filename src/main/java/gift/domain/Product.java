package gift.domain;


import gift.dto.request.ProductRequest;
import gift.dto.response.ProductResponse;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false, length = 15)
    private String name;

    @Column(nullable = false, name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<WishlistItem> wishlistItems;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public Product() { }

    public Product(String name, Integer price, String imageUrl, Category category) {
        this(null, name, price, imageUrl, category);
    }

    public Product(ProductRequest productRequest, Category category){
        this(null, productRequest.getName(), productRequest.getPrice(), productRequest.getImageUrl(), category);
    }

    public Product(Long id, String name, Integer price, String imageUrl, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(Long productId) {
        this.id = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }


    public Category getCategory() { return category; }

    public void update(ProductRequest productRequest, Category category){
        this.name = productRequest.getName();
        this.price = productRequest.getPrice();
        this.imageUrl = productRequest.getImageUrl();
        this.category = category;
    }

}