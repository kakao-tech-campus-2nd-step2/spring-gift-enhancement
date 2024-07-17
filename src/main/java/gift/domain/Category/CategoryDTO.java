package gift.domain.Category;

import jakarta.validation.constraints.NotBlank;

public class CategoryDTO {

    @NotBlank
    public String name;

    @NotBlank
    public String description;

    public CategoryDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
