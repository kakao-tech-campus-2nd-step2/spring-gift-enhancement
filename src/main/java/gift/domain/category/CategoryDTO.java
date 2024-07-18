package gift.domain.category;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class CategoryDTO {

    @NotBlank
    public String name;

    @NotBlank
    public String description;

    @JsonCreator
    public CategoryDTO(@JsonProperty("name") String name,
        @JsonProperty("description") String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
