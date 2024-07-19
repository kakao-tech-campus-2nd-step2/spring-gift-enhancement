package gift.service;

import gift.model.Category;
import gift.repository.CategoryRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElse(null);
    }

    public void addCategory(Category category) {
        categoryRepository.findByName(category.getName())
                .ifPresent(existingCategory -> {
                    throw new DuplicateKeyException("이미 존재하는 카테고리 이름입니다.");
                });
        categoryRepository.save(category);
    }

    public void updateCategory(Long id, Category category) {
        categoryRepository.findByName(category.getName())
                .ifPresent(existingCategory -> {
                    throw new DuplicateKeyException("이미 존재하는 카테고리 이름입니다.");
                });
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 카테고리입니다."));
        Category updatedCategory = new Category(
                existingCategory.getId(),
                category.getName(),
                category.getColor(),
                category.getImageUrl(),
                category.getDescription()
        );
        categoryRepository.save(updatedCategory);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @PostConstruct
    public void init() {
        Category noneCategory = new Category(1L, "없음");
        categoryRepository.save(noneCategory);
    }
}
