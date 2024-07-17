package gift.main.entity;

import gift.main.dto.ProductRequest;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "decimal(10,2) check (price >= 0)")
    private int price;

    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    //FetchType.LAZY는 객체를 getter쓸때 가져온다
    private List<WishProduct> wishProducts;
    //물건을 삭제하는 경우 -> 위시리스트 삭제...?


    public Product() {

    }

    public Product(ProductRequest productRequest, User seller, Category category) {
        this.name = productRequest.name();
        this.price = productRequest.price();
        this.imageUrl = productRequest.imageUrl();
        this.seller = seller;
        this.category = category;
    }

    public Product(String name, int price, String imageUrl, User seller, Category category) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.seller = seller;
        this.category = category;
    }

    public void updateValue(ProductRequest productRequest, Category category) {
        this.name = productRequest.name();
        this.price = productRequest.price();
        this.imageUrl = productRequest.imageUrl();
        this.category = category;
    }

    public void updateValue(String name, int price, String imageUrl, Category category) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
    }

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

    public String getSellerName() {
        return seller.getName();
    }

    public String getCategoryName() {
        return this.category.getName();
    }

    public int getCategoryUniNum() {
        return this.category.getUniNumber();
    }

    public User getSeller() {
        return seller;
    }

    public Category getCategory() {
        return category;
    }

    public List<WishProduct> getWishProducts() {
        return wishProducts;
    }
}
