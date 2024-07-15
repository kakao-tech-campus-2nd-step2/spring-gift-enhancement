package gift.entity;

import gift.exception.BadRequestExceptions.BadRequestException;
import gift.exception.BadRequestExceptions.BlankContentException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Objects;
import java.util.regex.Pattern;

@Entity
public class Category {

    private final Pattern COLOR_PATTERN = Pattern.compile("^#[0-9a-fA-F]{6}$");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private String description;

    protected Category() { }

    protected Category(Product product, String color, String imageUrl, String description) {
        validateColor(color);

        this.product = product;
        this.color = color;
        this.imageUrl = imageUrl;
        this.description = removeNullDescription(description);
    }

    public Long getId() { return id; }
    public Product getProduct() { return product; }
    public String getColor() { return color; }
    public String getImageUrl() { return imageUrl; }
    public String getDescription() { return description; }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Category category = (Category) object;
        return Objects.equals(getId(), category.getId()) && Objects.equals(
                getProduct(), category.getProduct()) && Objects.equals(getColor(),
                category.getColor()) && Objects.equals(getImageUrl(), category.getImageUrl())
                && Objects.equals(getDescription(), category.getDescription());
    }

    private void validateColor(String color){
        if(color.isBlank())
            throw new BlankContentException("색상을 입력해주세요");

        if(!COLOR_PATTERN.matcher(color).matches())
            throw new BadRequestException("색상 코드가 아닙니다.");
    }

    private String removeNullDescription(String description){
        if(description == null)
            return "";
        return description;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getProduct(), getColor(), getImageUrl(), getDescription());
    }
}
