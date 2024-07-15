package gift.Service;

import gift.ConverterToDto;
import gift.DTO.Category;
import gift.DTO.CategoryDto;
import gift.Repository.CategoryRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public List<CategoryDto> getAllCategories() {
    List<Category> categories = categoryRepository.findAll();
    List<CategoryDto> categoryDtos = categories.stream().map(ConverterToDto::convertToCategoryDto).toList();
    return categoryDtos;
  }
}
