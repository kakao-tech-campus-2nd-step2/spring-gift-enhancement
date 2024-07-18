package gift.product.category.service;

import gift.exception.CustomException;
import gift.exception.ErrorCode;
import gift.product.category.dto.request.CreateCategoryRequest;
import gift.product.category.dto.request.UpdateCategoryRequest;
import gift.product.category.dto.response.CategoryResponse;
import gift.product.category.entity.Categories;
import gift.product.category.entity.Category;
import gift.product.category.repository.CategoryRepository;
import gift.util.mapper.CategoryMapper;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream()
            .map(CategoryMapper::toResponse)
            .toList();
    }

    @Transactional(readOnly = true)
    public CategoryResponse getCategory(Long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
        return CategoryMapper.toResponse(category);
    }

    @Transactional
    public Long createCategory(CreateCategoryRequest request) {
        Category newCategory = new Category(request.name(), request.color(), request.description(),
            request.imageUrl());

        Categories categories = new Categories(categoryRepository.findAll());
        categories.validate(newCategory);

        return categoryRepository.save(newCategory).getId();
    }

    @Transactional
    public void updateCategory(Long id, UpdateCategoryRequest request) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
        category.edit(request);
    }

    @Transactional
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        categoryRepository.deleteById(id);
    }

}
