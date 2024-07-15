package gift.service;

import gift.converter.ProductConverter;
import gift.dto.PageRequestDTO;
import gift.dto.ProductDTO;
import gift.model.Product;
import gift.repository.ProductRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Pageable createPageRequest(PageRequestDTO pageRequestDTO) {
        Sort sort;
        if (pageRequestDTO.getDirection().equalsIgnoreCase(Sort.Direction.DESC.name())) {
            sort = Sort.by(pageRequestDTO.getSortBy()).descending();
        } else {
            sort = Sort.by(pageRequestDTO.getSortBy()).ascending();
        }

        return PageRequest.of(pageRequestDTO.getPage(), pageRequestDTO.getSize(), sort);
    }

    public Page<ProductDTO> findAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(ProductConverter::convertToDTO);
    }

    public Long addProduct(ProductDTO productDTO) {
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

        Product updatedProduct = new Product(existingProduct.getId(),
            ProductConverter.convertToEntity(productDTO).getName(),
            productDTO.getPrice(),
            productDTO.getImageUrl());

        productRepository.save(updatedProduct);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}