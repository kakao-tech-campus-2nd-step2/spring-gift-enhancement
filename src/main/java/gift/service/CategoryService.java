package gift.service;

import gift.domain.CategoryDTO;
import gift.entity.Category;
import gift.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        try {
            return categoryRepository.findAll();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Category findById(int id) {
        try {
            return categoryRepository.findById(id);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Can't find category with id " + id);
        }
    }

    public Category add(CategoryDTO categoryDTO) {
        Category category = new Category(categoryDTO.name(), categoryDTO.color(), categoryDTO.imgUrl(), categoryDTO.description());
        try {
            return categoryRepository.save(category);
        } catch (Exception e) {
            throw new RuntimeException("Can't save category " + categoryDTO);
        }
    }

    public Category update(int id, CategoryDTO categoryDTO) {

        Category category = new Category(id, categoryDTO.name(), categoryDTO.color(), categoryDTO.imgUrl(), categoryDTO.description());

        try {
            return categoryRepository.save(category);
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("Can't find category with id " + category.getId());
        }
    }

    public void delete(Integer id) {
        try {
            categoryRepository.deleteById(id);
        }
        catch (Exception e) {
            throw new RuntimeException("Can't delete category " + id);
        }
    }
}
