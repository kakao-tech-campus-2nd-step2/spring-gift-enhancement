package gift.service;


import gift.dto.ProductDto;
import gift.entity.Product;
import gift.exception.ProductNotFoundException;
import gift.repository.ProductRepository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Page<Product> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable);

    }

    public Product getProductById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            return optionalProduct.get();
        } else {
            throw ProductNotFoundException.of(id);
        }
    }

    public void addProduct(ProductDto productDto) {
        Product product = new Product(productDto.getName(), productDto.getPrice(), productDto.getImageUrl());
        productRepository.save(product);
    }

    public void updateProduct(Long id, ProductDto productDto) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> ProductNotFoundException.of(id));
        product.edit(productDto.getName(), productDto.getPrice(), productDto.getImageUrl());
        productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> ProductNotFoundException.of(id));
        productRepository.delete(product);
    }
}
