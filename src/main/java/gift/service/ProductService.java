package gift.service;

import gift.entity.Category;
import gift.dto.ProductDto;
import gift.entity.Product;
import gift.exception.ProductNotFoundException;
import gift.repository.ProductRepository;
import gift.repository.CategoryRepository;
import java.util.List;
import java.util.Optional;


import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    @Autowired
    public  ProductService(ProductRepository productRepository, CategoryService categoryService){
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    public Page<Product> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public void addProduct(ProductDto productDto) {
        Category category = categoryService.getCategory(productDto.getCategoryId());
        Product product = new Product(productDto.getName(), productDto.getPrice(), productDto.getImageUrl());
        product.setCategory(category);
        category.addProduct(product);
        productRepository.save(product);
    }

    public void updateProduct(Long id, ProductDto productDto) {
        Category category = categoryService.getCategory(productDto.getCategoryId());
        Product product = productRepository.findById(id)
                .orElseThrow(() -> ProductNotFoundException.of(id));
        product.edit(productDto.getName(), productDto.getPrice(), productDto.getImageUrl());
        product.setCategory(category);
        productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> ProductNotFoundException.of(id));

        Category category = product.getCategory();
        if (category != null) {
            category.removeProduct(product); // Remove product from category
        }
        productRepository.delete(product);
    }
}
