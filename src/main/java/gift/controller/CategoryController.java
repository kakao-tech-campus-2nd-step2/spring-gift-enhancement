package gift.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gift.model.Category;
import gift.service.CategoryService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	private final CategoryService categoryService;
	
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@GetMapping
	public ResponseEntity<List<Category>> getAllCategories(){
		List<Category> categories = categoryService.getAllCategories();
		return ResponseEntity.ok(categories);
	}
	
	@PostMapping
	public ResponseEntity<Category> addCategory(@Valid @RequestBody Category category, BindingResult bindingResult){
		Category createdCategory = categoryService.createCategory(category, bindingResult);
		return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> updateCategory(@PathVariable("id") Long id, @Valid Category category,
			BindingResult bindingResult){
		categoryService.updateCategory(id, category, bindingResult);
		return new ResponseEntity<>("Category updated successfully.", HttpStatus.OK);
	}
}
