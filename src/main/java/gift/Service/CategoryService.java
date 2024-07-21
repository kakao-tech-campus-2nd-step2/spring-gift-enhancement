package gift.Service;

import gift.Exception.CategoryNotFoundException;
import gift.Model.Category;
import gift.Model.RequestCategory;
import gift.Model.ResponseCategoryDTO;
import gift.Repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public Category addCategory(RequestCategory requestCategory) {
        Category category = new Category(requestCategory.name(), requestCategory.color(), requestCategory.imageUrl(), requestCategory.description());
        return categoryRepository.save(category);
    }
    @Transactional(readOnly = true)
    public List<ResponseCategoryDTO> getCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(it->new ResponseCategoryDTO(
                        it.getId(),
                        it.getName(),
                        it.getColor(),
                        it.getImageUrl(),
                        it.getDescription()))
                .toList();
    }

    @Transactional
    public void editCategory(RequestCategory requestCategory) {
        Category category = categoryRepository.findByName(requestCategory.name())
                .orElseThrow(()-> new CategoryNotFoundException("매칭되는 카테고리가 없습니다"));
        category.update(requestCategory.name(), requestCategory.color(), requestCategory.imageUrl(),requestCategory.description());;
    }

    @Transactional
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("매칭되는 카테고리가 없습니다"));
        categoryRepository.deleteById(categoryId);
    }
}
