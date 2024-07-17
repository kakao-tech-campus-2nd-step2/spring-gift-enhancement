package gift.domain.model.dto;

import gift.domain.model.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CategoryUpdateRequestDto {

    @NotNull
    private final Long id;

    @NotBlank
    private final String name;

    public CategoryUpdateRequestDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category toEntity() {
        return new Category(id, name);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
