package gift.mapper;

import gift.DTO.CategoryDTO;
import gift.entity.CategoryEntity;

public class CategoryMapper {
    public CategoryDTO toCategoryDTO(CategoryEntity categoryEntity) {
        return new CategoryDTO(
            categoryEntity.getId(),
            categoryEntity.getName()
        );
    }

    public CategoryEntity toCategoryEntity(CategoryDTO categoryDTO, boolean idRequired) {
        var categoryEntity = new CategoryEntity();
        if (idRequired) {
            categoryEntity.setId(categoryDTO.id());
        }
        categoryEntity.setName(categoryDTO.name());
        return categoryEntity;
    }

    public CategoryEntity toCategoryEntity(CategoryDTO categoryDTO) {
        return toCategoryEntity(categoryDTO, true);
    }
}
