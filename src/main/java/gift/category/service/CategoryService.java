package gift.category.service;
import gift.category.dto.CategoryDto;
import gift.category.repository.CategoryRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public List<CategoryDto> getAllCategories() {
    return categoryRepository.findAll().stream().map(CategoryDto::toDto)
        .collect(Collectors.toList());
  }

}
