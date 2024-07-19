package gift.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import gift.entity.Category;
import gift.exception.InvalidCategoryException;
import gift.repository.CategoryRepository;

@Service
public class CategoryService {
	
	private CategoryRepository categoryRepository;
	
	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	public List<Category> getAllCategories(){
		return categoryRepository.findAll();
	}
	
	public void createCategory(Category category, BindingResult bindingResult) {
		validateBindingResult(bindingResult);
		validateDuplicateCategoryName(category.getName());
		categoryRepository.save(category);
	}
	
	public void updateCategory(Long id, Category updatedCategory, BindingResult bindingResult) {
		validateBindingResult(bindingResult);
		validCategoryId(id, updatedCategory);
		validateCategoryId(id);
		categoryRepository.save(updatedCategory);
	}
	
	private void validateBindingResult(BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String errorMessage = bindingResult
					.getFieldError()
					.getDefaultMessage();
			throw new InvalidCategoryException(errorMessage, HttpStatus.BAD_REQUEST);
		}
	}
	
	private void validCategoryId(long id, Category updatedCategory) {
		if (!updatedCategory.getId().equals(id)) {
			throw new InvalidCategoryException("Category Id mismath.", HttpStatus.BAD_REQUEST);
		}
	}
	
	private void validateCategoryId(long id) {
		if (!categoryRepository.existsById(id)) {
			throw new InvalidCategoryException("Category not foudn.", HttpStatus.NOT_FOUND);
		}
	}
	
	private void validateDuplicateCategoryName(String categoryName) {
		if (categoryRepository.existsByName(categoryName)) {
			throw new DuplicateCategoryNameException("This is the extracted name.");
		}
	}
}
