package gift.service;

import gift.dto.ProductDto;
import gift.entity.Product;
import gift.exception.InvalidProductNameException;
import gift.exception.ProductNotFoundException;
import gift.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> findAll() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> new ProductDto(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getImgUrl()
                ))
                .collect(Collectors.toList());
    }

    public Optional<ProductDto> findById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(prod -> new ProductDto(
                prod.getId(),
                prod.getName(),
                prod.getPrice(),
                prod.getImgUrl()
        ));
    }

    public Long save(ProductDto productDto) {
        validateProductName(productDto.getName());
        Product product = new Product(productDto.getName(), productDto.getPrice(), productDto.getImgUrl());
        product = productRepository.save(product);
        return product.getId();
    }

    public void update(Long id, ProductDto productDto) {
        validateProductName(productDto.getName()); // 상품 이름 검증
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product updatedProduct = new Product(id, productDto.getName(), productDto.getPrice(), productDto.getImgUrl());
            productRepository.save(updatedProduct);
        } else {
            throw new ProductNotFoundException("상품을 찾을 수 없습니다. ID: " + id);
        }
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    private void validateProductName(String name) {
        if (name.contains("카카오")) {
            throw new InvalidProductNameException("상품명에 '카카오'를 포함할 경우, 담당 MD에게 문의바랍니다.");
        }
    }
}