package gift.model;

import static gift.util.constants.ProductConstants.NAME_INVALID_CHARACTERS;
import static gift.util.constants.ProductConstants.NAME_REQUIRES_APPROVAL;
import static gift.util.constants.ProductConstants.NAME_SIZE_LIMIT;
import static gift.util.constants.ProductConstants.OPTION_NAME_DUPLICATE;
import static gift.util.constants.ProductConstants.OPTION_REQUIRED;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 15)
    @Size(min = 1, max = 15, message = NAME_SIZE_LIMIT)
    @Pattern(
        regexp = "^[a-zA-Z0-9ㄱ-ㅎ가-힣\\(\\)\\[\\]\\+\\-\\&\\/\\_ ]*$",
        message = NAME_INVALID_CHARACTERS
    )
    @Pattern(
        regexp = "^(?!.*카카오).*$",
        message = NAME_REQUIRES_APPROVAL
    )
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false, name = "image_url", length = 255)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Option> options;

    public Product() {
    }

    public Product(Long id, String name, int price, String imageUrl, Category category,
        List<Option> options) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
        this.setOptions(options);
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

    public Long getCategoryId() {
        return category.getId();
    }

    public String getCategoryName() {
        return category.getName();
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        if (options == null || options.isEmpty()) {
            throw new IllegalArgumentException(OPTION_REQUIRED);
        }
        this.options = options;
        validateUniqueOptionNames();
    }

    private void validateUniqueOptionNames() {
        Set<String> uniqueNames = new HashSet<>();
        for (Option option : options) {
            if (!uniqueNames.add(option.getName())) {
                throw new IllegalArgumentException(OPTION_NAME_DUPLICATE);
            }
        }
    }

    public void update(String name, int price, String imageUrl, Category category,
        List<Option> options) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
        this.setOptions(options);
    }
}
