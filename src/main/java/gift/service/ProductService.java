package gift.service;

import static gift.controller.product.ProductMapper.from;
import static gift.controller.product.ProductMapper.toProductResponse;

import gift.controller.product.ProductMapper;
import gift.controller.product.ProductRequest;
import gift.controller.product.ProductResponse;
import gift.domain.Product;
import gift.exception.ProductAlreadyExistsException;
import gift.exception.ProductNotExistsException;
import gift.repository.ProductRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<ProductResponse> findAll(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        List<ProductResponse> productResponses = productPage.stream()
            .map(ProductMapper::toProductResponse).toList();
        return new PageImpl<>(productResponses, pageable, productPage.getTotalElements());
    }

    public ProductResponse find(UUID id) {
        Product target = productRepository.findById(id).orElseThrow(ProductNotExistsException::new);
        return toProductResponse(target);
    }

    public ProductResponse save(ProductRequest product) {
        productRepository.findByNameAndPriceAndImageUrl(product.name(), product.price(),
            product.imageUrl()).ifPresent(p -> {
            throw new ProductAlreadyExistsException();
        });
        return toProductResponse(productRepository.save(from(product)));
    }

    public ProductResponse update(UUID id, ProductRequest product) {
        Product target = productRepository.findById(id).orElseThrow(ProductNotExistsException::new);
        target.updateDetails(product.name(), product.price(), product.imageUrl());
        return toProductResponse(target);
    }

    public void delete(UUID id) {
        productRepository.findById(id).orElseThrow(ProductNotExistsException::new);
        productRepository.deleteById(id);
    }
}