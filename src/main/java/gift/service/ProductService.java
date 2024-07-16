package gift.service;

import gift.domain.Product;
import gift.dto.ProductRequestDTO;
import gift.dto.ProductResponseDTO;
import gift.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<ProductResponseDTO> getProducts(int page, int size, String[] sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sort[1]), sort[0]));
        Page<Product> productPage = productRepository.findAll(pageable);

        List<ProductResponseDTO> productResponseDTOList = productPage.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(productResponseDTOList, pageable, productPage.getTotalElements());
    }

    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product id: " + id));
        return convertToResponseDTO(product);
    }

    public void createProduct(ProductRequestDTO productRequestDTO) {
        Product product = new Product.Builder()
                .name(productRequestDTO.getName())
                .price(productRequestDTO.getPrice())
                .imageUrl(productRequestDTO.getImageUrl())
                .build();
        productRepository.save(product);
    }

    public void updateProduct(Long id, ProductRequestDTO productRequestDTO) {
        Product updatedProduct = new Product.Builder()
                .id(id)
                .name(productRequestDTO.getName())
                .price(productRequestDTO.getPrice())
                .imageUrl(productRequestDTO.getImageUrl())
                .build();
        productRepository.save(updatedProduct);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    private ProductResponseDTO convertToResponseDTO(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl()
        );
    }

}
