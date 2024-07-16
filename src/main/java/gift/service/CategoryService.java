package gift.service;

import gift.constants.ErrorMessage;
import gift.dto.CategoryDto;
import gift.entity.Category;
import gift.repository.CategoryJpaDao;
import gift.repository.ProductJpaDao;
import jakarta.transaction.Transactional;
import java.util.NoSuchElementException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryJpaDao categoryJpaDao;
    private final ProductJpaDao productJpaDao;

    public CategoryService(CategoryJpaDao categoryJpaDao, ProductJpaDao productJpaDao) {
        this.categoryJpaDao = categoryJpaDao;
        this.productJpaDao = productJpaDao;
    }

    /**
     * Pageable 객체를 통해 해당 페이지 객체를 반환.
     *
     * @param pageable
     * @return Page
     */
    public Page<CategoryDto> getCategoryPage(Pageable pageable) {
        return categoryJpaDao.findAll(pageable).map(CategoryDto::new);
    }

    /**
     * id로 카테고리 조회하여 반환
     *
     * @param id
     * @return CategoryDto
     */
    public CategoryDto getCategory(Long id) {
        Category category = checkCategoryExistsById(id);
        return new CategoryDto(category);
    }

    /**
     * 새 카테고리 추가
     *
     * @param categoryDto
     */
    public void addCategory(CategoryDto categoryDto) {
        checkCategoryDuplicationByName(categoryDto.getName());
        categoryJpaDao.save(new Category(categoryDto));
    }

    /**
     * 수정한 카테고리 정보로 수정
     *
     * @param categoryDto
     */
    @Transactional
    public void editCategory(CategoryDto categoryDto) {
        Category category = checkCategoryExistsById(categoryDto.getId());
        category.updateCategory(categoryDto);
    }

    /**
     * id에 해당하는 카테고리 삭제
     *
     * @param id
     */
    public void deleteCategory(Long id) {
        checkCategoryExistsById(id);
        categoryJpaDao.deleteById(id);
    }

    /**
     * 카테고리 id로 존재하는 카테고리인지 확인하고, 존재하면 객체 반환.
     *
     * @param id
     * @return Category 객체
     */
    private Category checkCategoryExistsById(Long id) {
        return categoryJpaDao.findById(id)
            .orElseThrow(() -> new NoSuchElementException(ErrorMessage.CATEGORY_NOT_EXISTS_MSG));
    }

    /**
     * 카테고리 이름으로 카테고리 중복 여부 확인.
     *
     * @param name
     */
    private void checkCategoryDuplicationByName(String name) {
        categoryJpaDao.findByName(name)
            .ifPresent(v -> {
                throw new IllegalArgumentException(ErrorMessage.CATEGORY_ALREADY_EXISTS_MSG);
            });
    }
}
