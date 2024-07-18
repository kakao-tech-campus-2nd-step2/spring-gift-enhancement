package gift.service;

import gift.domain.Category;
import gift.domain.Product;
import gift.dto.request.AddProductRequest;
import gift.dto.request.UpdateProductRequest;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static gift.constant.Message.*;

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

    public Page<Product> getAllProducts(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return productRepository.findAll(pageable);
    }

    public String addProduct(AddProductRequest requestProduct) {
        Category category = categoryRepository.findByName(requestProduct.getCategory()).get();
        productRepository.save(new Product(requestProduct, category));
        return ADD_SUCCESS_MSG;
    }

    public String updateProduct(Long productId, UpdateProductRequest product) {

        Product productToUpdate = productRepository.findProductById(productId).get();

        if (product.getName() != null) {
            productToUpdate.setName(product.getName());
        }
        if (product.getPrice() > 0) {
            productToUpdate.setPrice(product.getPrice());
        }
        if (product.getImageUrl() != null) {
            productToUpdate.setImageUrl(product.getImageUrl());
        }
        productRepository.save(productToUpdate);
        return UPDATE_SUCCESS_MSG;
    }

    public String deleteProduct(Long productId) {
        productRepository.delete(productRepository.findProductById(productId).get());
        return DELETE_SUCCESS_MSG;
    }

}
