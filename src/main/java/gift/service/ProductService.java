package gift.service;

import gift.converter.ProductConverter;
import gift.dto.PageRequestDTO;
import gift.dto.ProductDTO;
import gift.model.Product;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Page<ProductDTO> findAllProducts(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.toPageRequest();
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(ProductConverter::convertToDTO);
    }

    public Long addProduct(ProductDTO productDTO) {
        categoryRepository.findById(productDTO.getCategoryId())
            .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));
        Product product = ProductConverter.convertToEntity(productDTO);
        productRepository.save(product);
        return product.getId();
    }

    public Optional<ProductDTO> findProductById(Long id) {
        return productRepository.findById(id)
            .map(ProductConverter::convertToDTO);
    }

    public void updateProduct(ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(productDTO.getId())
            .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        categoryRepository.findById(productDTO.getCategoryId())
            .orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));

        existingProduct.update(
            ProductConverter.convertToEntity(productDTO).getName(),
            productDTO.getPrice(),
            productDTO.getImageUrl(),
            productDTO.getCategoryId()
        );

        productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}