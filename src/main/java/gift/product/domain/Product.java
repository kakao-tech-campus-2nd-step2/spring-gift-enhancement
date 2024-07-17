package gift.product.domain;

import gift.category.domain.Category;
import gift.exception.type.KakaoInNameException;
import gift.product.application.command.ProductUpdateCommand;
import jakarta.persistence.*;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 15)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public Product() {
    }

    public Product(String name, Integer price, String imageUrl, Category category) {
        this(null, name, price, imageUrl, category);
    }

    public Product(Long id, String name, Integer price, String imageUrl, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Category getCategory() {
        return category;
    }

    public void update(ProductUpdateCommand command, Category category) {
        this.name = command.name();
        this.price = command.price();
        this.imageUrl = command.imageUrl();
        this.category = category;
    }

    public void validateKakaoInName() {
        if (name.contains("카카오")) {
            throw new KakaoInNameException("카카오가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있습니다.");
        }
    }
}
