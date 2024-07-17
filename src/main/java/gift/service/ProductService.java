package gift.service;

import gift.domain.Category;
import gift.domain.Products;
import gift.dto.ProductRequestDTO;
import gift.dto.ProductResponseDTO;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Page<ProductResponseDTO> getProducts(int page, int size, String[] sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sort[1]), sort[0]));
        Page<Products> productPage = productRepository.findAll(pageable);

        List<ProductResponseDTO> productResponseDTOList = productPage.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(productResponseDTOList, pageable, productPage.getTotalElements());
    }

    public ProductResponseDTO getProductById(Long id) {
        Products product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product id: " + id));
        return convertToResponseDTO(product);
    }

    public void createProduct(ProductRequestDTO productRequestDTO) {
        Category category = categoryRepository.findById(productRequestDTO.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category id: " + productRequestDTO.getCategoryId()));

        Products product = new Products.Builder()
                .name(productRequestDTO.getName())
                .price(productRequestDTO.getPrice())
                .imageUrl(productRequestDTO.getImageUrl())
                .category(category)
                .build();
        productRepository.save(product);
    }

    public void updateProduct(Long id, ProductRequestDTO productRequestDTO) {
        Category category = categoryRepository.findById(productRequestDTO.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category id: " + productRequestDTO.getCategoryId()));

        Products updatedProduct = new Products.Builder()
                .id(id)
                .name(productRequestDTO.getName())
                .price(productRequestDTO.getPrice())
                .imageUrl(productRequestDTO.getImageUrl())
                .category(category)
                .build();
        productRepository.save(updatedProduct);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    private ProductResponseDTO convertToResponseDTO(Products product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl(),
                product.getCategory().getName(),
                product.getCategory().getId() // Add this line
        );
    }
}
