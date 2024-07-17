package gift.service;

import gift.dto.CategoryDto;
import gift.exception.ResourceNotFoundException;
import gift.model.Category;
import gift.model.Product;
import gift.repository.CategoryRepository;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public ArrayList<CategoryDto> getAllCategories() {
        var allCategories = categoryRepository.findAll();
        var categoryDtoList = new ArrayList<CategoryDto>();
        for (Category category : allCategories) {
            var categoryNameList = new ArrayList<String>();
            for (Product product : category.getProductList()) {
                categoryNameList.add(product.getName());
            }
            categoryDtoList.add(
                new CategoryDto(category.getId(), category.getName(), categoryNameList));
        }
        return categoryDtoList;
    }

    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category getCategoryById(Long id) {
        var foundCategoryById = categoryRepository.findById(id);
        if (foundCategoryById.isEmpty()) {
            throw new ResourceNotFoundException("없는 카테고리 입니다.");
        }
        return foundCategoryById.get();
    }

    public CategoryDto updateCategory(Long id, Category category) {
        var foundCategory = categoryRepository.findById(id);
        if (foundCategory.isEmpty()) {
            throw new ResourceNotFoundException("없는 카테고리 입니다.");
        }
        var updatedCategory = foundCategory.get();
        updatedCategory.setName(category.getName());
        var nameList = new ArrayList<String>();
        var savedCategory = categoryRepository.save(updatedCategory);
        for (Product product : savedCategory.getProductList()) {
            nameList.add(product.getName());
        }
        return new CategoryDto(savedCategory.getId(), savedCategory.getName(), nameList);
    }

    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }
}
