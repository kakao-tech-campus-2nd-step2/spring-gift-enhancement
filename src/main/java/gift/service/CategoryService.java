package gift.service;

import gift.repository.CategoryRepository;
import gift.vo.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> getCategories() {
        return repository.findAll();
    }

    public Category getCategoryById(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("선택하신 카테고리를 찾을 수 없습니다."));
    }

    public void deleteCategoryById(Long categoryId) {
        repository.deleteById(categoryId);
    }
}
