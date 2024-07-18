package gift.service;

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

    public void createProduct(Product product, BindingResult bindingResult) {
    	validateBindingResult(bindingResult);
    	validateCategory(product.getCategory().getId());
        productRepository.save(product);
    }

    public void updateProduct(long id, Product updatedProduct, BindingResult bindingResult) {
    	validateBindingResult(bindingResult);
    	validProductId(id, updatedProduct);
    	validateProductId(id);
    	validateCategory(updatedProduct.getCategory().getId());
    	productRepository.save(updatedProduct);
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
    
    private void validProductId(long id, Product updatedProduct) {
    	if (!updatedProduct.getId().equals(id)) {
    		throw new InvalidProductException("Product Id mismatch.");
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
    
    public void validateCategory(Long categoryId) {
    	if (!categoryRepository.existsById(categoryId)) {
    		throw new InvalidProductException("Category not found");
    	}
    }
}
