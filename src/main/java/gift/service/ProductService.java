package gift.service;

import gift.dto.OptionRequestDto;
import gift.dto.ProductRequestDto;
import gift.dto.ProductResponseDto;
import gift.entity.Category;
import gift.entity.Option;
import gift.entity.Product;
import gift.exception.BusinessException;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Page<ProductResponseDto> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findAll(pageable);

        List<ProductResponseDto> productResponseDtoList = productPage.stream()
            .map(product -> new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl(),
                product.getCategory().getId()
            ))
            .collect(Collectors.toList());

        return new PageImpl<>(productResponseDtoList, pageable, productPage.getTotalElements());
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new BusinessException("해당 아이디에 대한 상품이 존재하지 않습니다."));
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product update(Long id, Product product) {
        return productRepository.findById(id)
            .map(existingProduct -> {
                existingProduct.setName(product.getName());
                existingProduct.setPrice(product.getPrice());
                existingProduct.setImageUrl(product.getImageUrl());
                return productRepository.save(existingProduct);
            }).orElseThrow(() -> new BusinessException("해당 아이디에 대한 상품이 존재하지 않습니다."));
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
