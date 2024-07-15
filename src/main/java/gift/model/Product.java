package gift.model;

import static gift.util.Constants.PRODUCT_NAME_REQUIRES_APPROVAL;
import static gift.util.Constants.PRODUCT_NAME_INVALID_CHARACTERS;
import static gift.util.Constants.PRODUCT_NAME_SIZE_LIMIT;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 15)
    @Size(min = 1, max = 15, message = PRODUCT_NAME_SIZE_LIMIT)
    @Pattern(
        regexp = "^[a-zA-Z0-9ㄱ-ㅎ가-힣\\(\\)\\[\\]\\+\\-\\&\\/\\_ ]*$",
        message = PRODUCT_NAME_INVALID_CHARACTERS
    )
    @Pattern(
        regexp = "^(?!.*카카오).*$",
        message = PRODUCT_NAME_REQUIRES_APPROVAL
    )
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false, name = "image_url")
    private String imageUrl;

    protected Product() {
    }

    public Product(Long id, String name, int price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
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

    public void update(String name, int price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }
}
