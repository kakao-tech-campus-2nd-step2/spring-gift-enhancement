package gift.product.entity;

import gift.category.entity.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

    // 처음에는 id로 설정했다가 헷갈려서 productId로 쓰기로 하였습니다..
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String image;

    @ManyToOne
    private Category category;

    protected Product() {
    }

    public Product(String name, int price, String image) {
        this.productId = null;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public Product(Long productId, String name, int price, String image) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public void updateProduct(String name, int price, String image) {
        this.name = name;
        this.price = price;
        this.image = image;
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

    public String getImage() {
        return image;
    }
}
