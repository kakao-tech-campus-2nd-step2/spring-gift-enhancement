package gift.product.model;

import gift.category.model.Category;
import gift.wishlist.model.WishList;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = true)
    private String imgUrl;

    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WishList> wishLists = new ArrayList<>();

    // JPA에서 필요로 하는 기본 생성자
    protected Product() {
    }

    public Product(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public Product(String name, int price, String imgUrl, Category category) {
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
        this.category = category;
    }

    // ID를 포함한 생성자
    public Product(Long productId, String name, String imgUrl, int price, Category category) {
        this.productId = productId;
        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
        this.category = category;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Category getCategory() {
        return category;
    }

    // 카테고리 ID를 반환하는 메소드 추가
    public Long getCategoryId() {
        return category.getCategoryId();
    }

    // update 메소드 추가
    public void update(String name, int price, String imgUrl, Category category) {
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
        this.category = category;
    }
}