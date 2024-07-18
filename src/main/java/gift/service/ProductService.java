package gift.service;

import gift.constants.Messages;
import gift.domain.Category;
import gift.domain.Product;
import gift.dto.ProductRequestDto;
import gift.exception.ProductNotFoundException;
import gift.repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public ProductService(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @Transactional
    public Product save(ProductRequestDto productRequestDto) {
        Category category = categoryService.findById(productRequestDto.getCategoryId());
        return productRepository.save(productRequestDto.toEntity(category));
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Product findById(Long id) throws ProductNotFoundException {
        return productRepository.findById(id).get();
    }

    @Transactional
    public void updateProduct(Long id, ProductRequestDto productRequestDto) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(
                Messages.NOT_FOUND_PRODUCT_MESSAGE));
        Category updatedcategory = categoryService.findById(productRequestDto.getCategoryId());
        product.setName(productRequestDto.getName());
        product.setPrice(productRequestDto.getPrice());
        product.setImageUrl(productRequestDto.getImageUrl());
        product.setCategory(updatedcategory);
        productRepository.save(product);
    }

    public void deleteById(Long id) throws ProductNotFoundException {
        productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(
            Messages.NOT_FOUND_PRODUCT_MESSAGE));
        productRepository.deleteById(id);
    }
}
