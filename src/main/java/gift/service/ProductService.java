package gift.service;

import gift.dto.CategoryUpdateRequest;
import gift.dto.ProductRequest;
import gift.dto.productUpdateRequest;
import gift.entity.Category;
import gift.entity.Product;
import gift.exception.InvalidProductException;
import gift.exception.InvalidUserException;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class ProductService {

	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;
	
	public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
	}
	
    public Page<Product> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product getProduct(long id) {
        return findProductById(id);
    }

    public void createProduct(ProductRequest request, BindingResult bindingResult) {
    	validateBindingResult(bindingResult);
    	Category category = validateCategory(request.getCategoryName());
    	Product product = request.toEntity(category);
        productRepository.save(product);
    }

    public void updateProduct(long id, productUpdateRequest request, BindingResult bindingResult) {
    	validateBindingResult(bindingResult);
    	Product updateProduct = findProductById(id);
    	request.updateEntity(updateProduct);
    	productRepository.save(updateProduct);
    }
    
    public void updateProductCategory(long id, CategoryUpdateRequest request, 
    		BindingResult bindingResult) {
    	validateBindingResult(bindingResult);
    	Product updateProduct = findProductById(id);
    	Category category = validateCategory(request.getCategoryName());
    	updateProduct.setCategory(category);
    	productRepository.save(updateProduct);
    }

    public void deleteProduct(long id) {
    	validateProductId(id);
        productRepository.deleteById(id);
    }
    
    private void validateBindingResult(BindingResult bindingResult) {
    	if (bindingResult.hasErrors()) {
    		String errorMessage = bindingResult
					.getFieldError()
					.getDefaultMessage();
			throw new InvalidUserException(errorMessage, HttpStatus.BAD_REQUEST);
    	}
    }
    
    private void validateProductId(long id) {
    	if (!productRepository.existsById(id)) {
    		throw new InvalidProductException("Product not found");
    	}
    }
    
    public Product findProductById(long id) {
	    return productRepository.findById(id)
	    		.orElseThrow(() -> new InvalidProductException("Product not found"));
    }
    
    public Category validateCategory(String categoryName) {
    	return categoryRepository.findByName(categoryName)
    			.orElseThrow(() -> new InvalidProductException("Category not found"));
    }
}
