package gift.category;

import jakarta.validation.constraints.NotBlank;

public class CategoryDTO {

    long id;
    @NotBlank(message = "이름을 입력하지 않았습니다.")
    String name;

    public CategoryDTO(long id, String name){
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category toCategory() {
        return new Category(id, name);
    }

    public static CategoryDTO fromCategory(Category category) {
        return new CategoryDTO(category.getId(), category.getName());
    }
}
