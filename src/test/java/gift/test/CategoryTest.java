package gift.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import gift.controller.CategoryController;
import gift.model.Category;
import gift.service.CategoryService;

public class CategoryTest {
	
	@Mock
	private CategoryService categoryService;
	
	@InjectMocks
	private CategoryController categoryController;
	
	@Mock
	private BindingResult bindingResult;
	
	private Category category;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		category = new Category("교환권", "#6c95d1", "https://example.com/image.jpg", "");
	}
	
	@Test
	public void testGetCategories() {
		List<Category> categories = Collections.singletonList(category);
		
		when(categoryService.getAllCategories()).thenReturn(categories);
		ResponseEntity<List<Category>> response = categoryController.getAllCategories();
		
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
		assertThat(response.getBody()).isEqualTo(categories);
	}
	
	@Test
	public void testAddCategory() {
		when(categoryService.createCategory(any(Category.class), any(BindingResult.class))).thenReturn(category);
		ResponseEntity<Category> response = categoryController.addCategory(category, bindingResult);
		
		assertThat(response.getStatusCodeValue()).isEqualTo(HttpStatus.CREATED.value());
		assertThat(response.getBody()).isEqualTo(category);
	}
	
	@Test
	public void testUpdateCategory() {
		doReturn(null).when(categoryService).updateCategory(eq(1L), any(Category.class), any(BindingResult.class));
		
		ResponseEntity<String> response = categoryController.updateCategory(1L, category, bindingResult);
	
		assertThat(response.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getBody()).isEqualTo("Category updated successfully.");
	}
}
