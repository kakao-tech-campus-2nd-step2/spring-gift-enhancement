package gift.service;

import gift.domain.Category;
import gift.domain.Product;
import gift.dto.request.AddProductRequest;
import gift.dto.request.UpdateProductRequest;
import gift.exception.CustomException;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static gift.constant.Message.*;
import static gift.exception.ErrorCode.DATA_NOT_FOUND;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Product getProduct(Long productId) {
        return productRepository.findProductById(productId).orElse(null);
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public String addProduct(AddProductRequest requestProduct) {
        Category category = categoryRepository.findByName(requestProduct.getCategory()).get();
        productRepository.save(new Product(requestProduct, category));
        return ADD_SUCCESS_MSG;
    }

    public String updateProduct(Long productId, UpdateProductRequest productRequest) {
        Product existingProduct = productRepository.findProductById(productId).orElseThrow(() -> new CustomException(DATA_NOT_FOUND));
        Category category = categoryRepository.findByName(productRequest.getCategory()).orElseThrow(() -> new CustomException(DATA_NOT_FOUND));
        productRepository.save(new Product(existingProduct.getId(), productRequest, category));
        return UPDATE_SUCCESS_MSG;
    }

    public String deleteProduct(Long productId) {
        productRepository.delete(productRepository.findProductById(productId).get());
        return DELETE_SUCCESS_MSG;
    }

}
