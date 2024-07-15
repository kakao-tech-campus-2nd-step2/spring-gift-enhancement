package gift.entity;

import gift.exception.BadRequestExceptions.BadRequestException;
import gift.exception.BadRequestExceptions.BlankContentException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;
import java.util.regex.Pattern;

@Entity
public class Category {

    private final Pattern COLOR_PATTERN = Pattern.compile("^#[0-9a-fA-F]{6}$");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private String description;

    protected Category() { }

    public Category(String color, String imageUrl, String description) {
        validateColor(color);
        validateImageUrl(imageUrl);

        this.color = color;
        this.imageUrl = imageUrl;
        this.description = convertNullToBlankDescription(description);
    }

    public Long getId() { return id; }
    public String getColor() { return color; }
    public String getImageUrl() { return imageUrl; }
    public String getDescription() { return description; }


    private void validateColor(String color){
        if(color.isBlank())
            throw new BlankContentException("색상을 입력해주세요");

        if(!COLOR_PATTERN.matcher(color).matches())
            throw new BadRequestException("색상 코드가 아닙니다.");
    }

    private void validateImageUrl(String imageUrl){
        if(imageUrl.isBlank())
            throw new BlankContentException("이미지 url을 입력해주세요.");
    }

    private String convertNullToBlankDescription(String description){ // 순수 로직이므로 추후에 util로 옮길 필요성이 있어보임
        if(description == null)
            return "";
        return description;
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Category category = (Category) object;
        return Objects.equals(COLOR_PATTERN, category.COLOR_PATTERN)
                && Objects.equals(getId(), category.getId()) && Objects.equals(
                getColor(), category.getColor()) && Objects.equals(getImageUrl(),
                category.getImageUrl()) && Objects.equals(getDescription(),
                category.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(COLOR_PATTERN, getId(), getColor(), getImageUrl(), getDescription());
    }
}
