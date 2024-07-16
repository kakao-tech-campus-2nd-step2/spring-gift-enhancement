package gift.service.product;

import gift.controller.product.dto.CategoryResponse;
import gift.controller.product.dto.CategoryResponse.Info;
import gift.global.validate.NotFoundException;
import gift.repository.product.CategoryRepository;
import gift.service.product.dto.CategoryCommand;
import gift.service.product.dto.CategoryModel;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryModel.Info createCategory(CategoryCommand.Register command) {
        categoryRepository.findByName(command.name()).ifPresent(category -> {
            throw new IllegalArgumentException("Category already exists");
        });
        var category = categoryRepository.save(command.toEntity());
        return CategoryModel.Info.from(category);
    }

    @Transactional
    public CategoryModel.Info updateCategory(Long id, CategoryCommand.Update command) {
        var category = categoryRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Category not found"));
        category.update(command.name());
        return CategoryModel.Info.from(category);
    }

    @Transactional
    public void deleteCategory(Long id) {
        categoryRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Category not found"));

        categoryRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<CategoryModel.Info> getCategories() {
        return categoryRepository.findAll().stream()
            .map(CategoryModel.Info::from)
            .toList();
    }

    @Transactional(readOnly = true)
    public CategoryModel.Info getCategory(Long id) {
        var category = categoryRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Category not found"));
        return CategoryModel.Info.from(category);
    }


}
