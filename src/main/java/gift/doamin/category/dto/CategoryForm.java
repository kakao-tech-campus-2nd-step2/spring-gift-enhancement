package gift.doamin.category.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.NotBlank;

public class CategoryForm {

    private Long id;

    @NotBlank
    private String name;

    @JsonCreator
    public CategoryForm(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
