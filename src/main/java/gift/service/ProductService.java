package gift.service;

import gift.dto.ProductRequestDTO;
import gift.dto.ProductResponseDTO;
import gift.entity.Category;
import gift.entity.Product;
import gift.exception.categortException.CategoryNotFoundException;
import gift.exception.ProductException;
import gift.exception.productException.ProductNotFoundException;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Product getProduct(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        return product.orElseThrow(() -> new ProductNotFoundException(productId));
    }

    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductService::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void saveProduct(ProductRequestDTO productRequestDTO){
        Long categoryId = productRequestDTO.categoryId();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        category.addProduct(productRequestDTO);
    }


    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        productRepository.deleteById(productId);
    }

    @Transactional
    public void updateProduct(Long productId, ProductRequestDTO productRequestDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        product.updateProduct(productRequestDTO);
    }

    public static Product toEntity(ProductRequestDTO dto, Category category) {
        Product product = new Product();
        product.setName(dto.name());
        product.setPrice(dto.price());
        product.setImageUrl(dto.imageUrl());
        product.setCategory(category);
        return product;
    }

    private static ProductResponseDTO toDto(Product product){
        return new ProductResponseDTO(product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl(),
                product.getCategory()
                        .getName());
    }

}
