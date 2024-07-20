package gift.category;

import gift.category.model.Category;
import gift.category.model.CategoryRequestDto;
import gift.category.model.CategoryResponseDto;
import gift.common.exception.CategoryException;
import gift.product.ProductRepository;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public CategoryService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoryResponseDto> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable)
            .map(CategoryResponseDto::from)
            .getContent();
    }

    @Transactional(readOnly = true)
    public CategoryResponseDto getCategoryById(Long id) throws CategoryException {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new CategoryException(CategoryErrorCode.NOT_FOUND));
        return CategoryResponseDto.from(category);
    }

    @Transactional
    public Long insertCategory(CategoryRequestDto categoryRequestDto) {
        Category category = new Category(categoryRequestDto.name(), categoryRequestDto.color(), categoryRequestDto.imageUrl(), categoryRequestDto.description());
        category = categoryRepository.save(category);
        return category.getId();
    }

    @Transactional
    public void updateCategory(CategoryRequestDto categoryRequestDto, Long id) throws CategoryException {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new CategoryException(CategoryErrorCode.NOT_FOUND));
        category.updateInfo(categoryRequestDto.name(), categoryRequestDto.color(), categoryRequestDto.imageUrl(), categoryRequestDto.description());
    }

    @Transactional
    public void deleteCategory(Long id) throws CategoryException {
        if(productRepository.existsByCategoryId(id)){
            throw new CategoryException(CategoryErrorCode.CAN_NOT_DELETE);
        }
        categoryRepository.deleteById(id);
    }
}
