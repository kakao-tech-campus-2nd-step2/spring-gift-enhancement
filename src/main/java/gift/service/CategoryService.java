package gift.service;

import gift.model.Category;
import gift.model.CategoryDto;
import gift.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public Category saveCategory(Category category) {
    return categoryRepository.save(category);
  }

  public List<Category> findAll() {
    return categoryRepository.findAll();
  }
  public Category convertToEntity(CategoryDto categoryDto) {
    Category category = new Category();
    category.setName(categoryDto.getName());
    category.setColor(categoryDto.getColor());
    category.setImageUrl(categoryDto.getImageUrl());
    category.setDescription(categoryDto.getDescription());
    return category;
  }

  public CategoryDto convertToDto(Category category) {
    return new CategoryDto(category.getId(), category.getName(), category.getColor(), category.getImageUrl(), category.getDescription());
  }

}