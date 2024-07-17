package gift.entity;

import gift.dto.request.AddProductRequest;
import jakarta.persistence.*;

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
    private Integer price;
    @Column(nullable = false)
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false, foreignKey = @ForeignKey(name = "fk_product_category_id_ref_category_id"))
    private Category category;
    @OneToMany(cascade = CascadeType.ALL)
    private final List<Option> options = new ArrayList<>();

    protected Product() {
    }

    public Product(Long id, String name, Integer price, String imageUrl, Category category, List<Option> options) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
        this.options.addAll(options);
    }

    public Product(String name, Integer price, String imageUrl, Category category, List<Option> options) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
        this.options.addAll(options);
    }

    public Product(AddProductRequest request, Category category, List<Option> options) {
        this.name = request.name();
        this.price = request.price();
        this.imageUrl = request.imageUrl();
        this.category = category;
        this.options.addAll(options);
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

    public List<Option> getOptions() {
        return options;
    }

    public void update(String name, int price, String imageUrl, Category category) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
    }
}
