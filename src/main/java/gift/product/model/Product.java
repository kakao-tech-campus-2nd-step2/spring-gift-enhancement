package gift.product.model;

import gift.category.model.Category;
import gift.option.model.Option;
import gift.wishlist.model.WishList;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private ProductName name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = true)
    private String imgUrl;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WishList> wishLists = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Option> options;

    protected Product() {}

    public Product(ProductName name, int price, String imgUrl, Category category) {
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public ProductName getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void update(ProductName name, int price, String imgUrl, Category category) {
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
        this.category = category;
    }
}