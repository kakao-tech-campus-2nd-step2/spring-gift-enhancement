package gift.doamin.category.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class CategoryForm {

    private String name;

    @JsonCreator
    public CategoryForm(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
