package gift.util.validator.databaseValidator;

import gift.entity.Category;
import gift.exception.BadRequestExceptions.BadRequestException;
import gift.repository.CategoryRepository;
import javax.crypto.BadPaddingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryDatabaseValidator {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryDatabaseValidator(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category validate(String categoryName) {
        return categoryRepository.findByName(categoryName).orElseThrow(() -> new BadRequestException("그러한 카테코리를 찾을 수 없습니다."));
    }

}
