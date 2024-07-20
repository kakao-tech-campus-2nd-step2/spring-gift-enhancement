package gift.administrator.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CategoryDTO {

    private Long id;
    @NotBlank(message = "이름을 입력하지 않았습니다.")
    private String name;
    @Pattern(regexp = "(^#(([0-9a-fA-F]{2}){3}|([0-9a-fA-F]){3})$)"
        + "|(^#[0-9a-fA-F]{8}$|#[0-9a-fA-F]{6}$|#[0-9a-fA-F]{4}$|#[0-9a-fA-F]{3}$)"
        , message = "컬러코드가 아닙니다.")
    private String color;
    private String imageUrl;
    private String description;

    public CategoryDTO() {
    }

    public CategoryDTO(Long id, String name, String color, String imageUrl, String description) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public CategoryDTO(String name, String color, String imageUrl, String description) {
        this.name = name;
        this.color = color;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getColor(){return color;}

    public String getImageUrl(){return imageUrl;}

    public String getDescription(){return description;}

    public Category toCategory() {
        return new Category(id, name, color, imageUrl, description);
    }

    public static CategoryDTO fromCategory(Category category) {
        return new CategoryDTO(category.getId(), category.getName(), category.getColor(),
            category.getImageUrl(), category.getDescription());
    }
}
