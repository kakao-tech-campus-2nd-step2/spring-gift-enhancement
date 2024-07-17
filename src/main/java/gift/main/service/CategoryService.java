package gift.main.service;

import gift.main.Exception.CustomException;
import gift.main.Exception.ErrorCode;
import gift.main.dto.CategoryRequest;
import gift.main.entity.Category;
import gift.main.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategoryAll() {
        return categoryRepository.findAll();
    }

    @Transactional
    public void addCategory(CategoryRequest categoryRequest) {
        if(categoryRepository.existsByName(categoryRequest.name())){
            throw new CustomException(ErrorCode.ALREADY_CATEGORY_NAME);
        }
        if(categoryRepository.existsByUniNumber(categoryRequest.uniNumber())){
            throw new CustomException(ErrorCode.ALREADY_CATEGORY_UNI_NUMBER);
        }
        Category category = new Category(categoryRequest);
        categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(long id) {

    }

    @Transactional
    public void updateCategory(Long categoryid, CategoryRequest categoryRequest) {
        if(categoryRepository.existsByName(categoryRequest.name())){
            throw new CustomException(ErrorCode.ALREADY_CATEGORY_NAME);
        }
        if(categoryRepository.existsByUniNumber(categoryRequest.uniNumber())){
            throw new CustomException(ErrorCode.ALREADY_CATEGORY_UNI_NUMBER);
        }
        Category category = categoryRepository.findById(categoryid)
                .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));
        category.updateCategory(categoryRequest);
        categoryRepository.save(category);
    }


}
