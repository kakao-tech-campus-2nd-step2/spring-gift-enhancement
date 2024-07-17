package gift.domain;

import gift.response.ProductResponse;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String imageUrl;

    @JoinColumn(name = "category_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Option> options = new ArrayList<>();

    public Product() {
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

    public void setId(Long id) {
        this.id = id;
    }

    public ProductResponse toDto() {
        return new ProductResponse(this.getId(), this.getName(), this.getPrice(),
                this.getImageUrl());
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changePrice(int price) {
        this.price = price;
    }

    public void changeImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void changeCategory(Category category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Product(String name, int price, String imageUrl, Category category) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
    }

}
