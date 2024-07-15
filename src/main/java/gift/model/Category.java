package gift.model;

import gift.exception.InputException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.UniqueConstraint;

@Entity
public class Category extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String color;
    @Column(name = "image_url", nullable = false)
    private String imageUrl;
    private String description;

    protected Category() {
    }

    public Category(String name, String color, String imageUrl, String description) {
        this.name = name;
        this.color = color;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void updateCategory(String name, String color, String imageUrl, String description) {
        validateName(name);
        validateColor(color);
        validateImageUrl(imageUrl);
        validateDescription(description);

        this.name = name;
        this.color = color;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    private void validateName(String name) {
        if( name == null || name.isEmpty() || name.length() > 20) {
            throw new InputException("이름을 1~20자 사이로 입력해주세요");
        }
    }

    private void validateColor(String color) {
        if( color == null || color.isEmpty() || color.length() > 10) {
            throw new InputException("색상을 올바르게 입력해주세요.");
        }
    }

    private void validateImageUrl(String imageUrl) {
        if (imageUrl == null || imageUrl.isBlank()) {
            throw new InputException("이미지 주소를 입력해주세요.");
        }
        if (!imageUrl.matches("^(https?)://[^ /$.?#].[^ ]*$")) {
            throw new InputException("올바른 url이 아닙니다.");
        }
    }

    private void validateDescription(String description) {
        if(description != null && !description.isEmpty()) {
            if (description.length() > 50) {
                throw new InputException("설명은 50자를 초과할 수 없습니다.");
            }
        }
    }


}
