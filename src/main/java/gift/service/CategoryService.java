package gift.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import gift.exception.InvalidCategoryException;
import gift.model.Category;
import gift.repository.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<Category> getAllCategories(){
		return categoryRepository.findAll();
	}
	
	public Category createCategory(Category category, BindingResult bindingResult) {
		validateBindingResult(bindingResult);
		return categoryRepository.save(category);
	}
	
	public Category updateCategory(Long id, Category updatedCategory, BindingResult bindingResult) {
		validateBindingResult(bindingResult);
		validCategoryId(id, updatedCategory);
		validateCategoryId(id);
		return categoryRepository.save(updatedCategory);
	}
	
	private void validateBindingResult(BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			String errorMessage = bindingResult
					.getFieldError()
					.getDefaultMessage();
			throw new InvalidCategoryException(errorMessage, HttpStatus.BAD_REQUEST);
		}
	}
	
	private void validCategoryId(long id, Category updatedCategory) {
		if(!updatedCategory.getId().equals(id)) {
			throw new InvalidCategoryException("Category Id mismath.", HttpStatus.BAD_REQUEST);
		}
	}
	
	private void validateCategoryId(long id) {
		if(!categoryRepository.existsById(id)) {
			throw new InvalidCategoryException("Category not foudn.", HttpStatus.NOT_FOUND);
		}
	}
}
