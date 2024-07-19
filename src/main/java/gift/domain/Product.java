package gift.domain;

import gift.dto.request.ProductRequest;
import gift.dto.request.UpdateProductRequest;
import gift.util.ProductValidationUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

import static gift.constant.Message.*;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = REQUIRED_FIELD_MSG)
    @Size(max = 15, message = LENGTH_ERROR_MSG)
    @Column(nullable = false)
    private String name;

    @NotNull(message = REQUIRED_FIELD_MSG)
    @Positive(message = POSITIVE_NUMBER_REQUIRED_MSG)
    @Column(nullable = false)
    private int price;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Wish> wishes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Option> options = new ArrayList<>();

    public Product() {
    }

    public Product(long id, String name, int price, String imageUrl) {
        ProductValidationUtil.isValidProductName(name);
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product(ProductRequest productRequest, Category category, Option option) {
        ProductValidationUtil.isValidProductName(productRequest.getName());
        this.name = productRequest.getName();
        this.price = productRequest.getPrice();
        this.imageUrl = productRequest.getImageUrl();
        this.category = category;
        this.options.add(option);
    }

    public Product(Long id, UpdateProductRequest productRequest, Category category) {
        ProductValidationUtil.isValidProductName(productRequest.getName());
        this.id = id;
        this.name = productRequest.getName();
        this.price = productRequest.getPrice();
        this.imageUrl = productRequest.getImageUrl();
        this.category = category;
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

    public String getCategory() {
        return category.getName();
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        ProductValidationUtil.isValidProductName(name);
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}