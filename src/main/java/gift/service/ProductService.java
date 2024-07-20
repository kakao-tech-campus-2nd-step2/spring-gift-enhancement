package gift.service;

import gift.domain.Option;
import gift.domain.Product;
import gift.dto.CreateProductDto;
import gift.dto.UpdateProductDto;
import gift.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final OptionService optionService;

    public ProductService(ProductRepository productRepository, OptionService optionService) {
        this.productRepository = productRepository;
        this.optionService = optionService;
    }

    public Product createProduct(CreateProductDto productDto) {
        Product product = productDto.toProduct();
        productRepository.save(product);
        return product;
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product getProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow();
        return product;
    }

    public Product updateProduct(Long productId, UpdateProductDto productDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("해당 product가 존재하지 않습니다."));
        updateProductOptions(product,productDto.getOptions());
        productRepository.save(product);
        return product;
    }

    private void updateProductOptions(Product product, List<Option> updateOptions) {
        List<Option> originOptions = product.getOptions();
        List<Option> optionList = updateOptions.stream()
                .map(optionDto -> {
                    Option originOption = originOptions.stream()
                            .filter(o -> o.getId().equals(optionDto.getId()))
                            .findFirst()
                            .orElse(null);
                    if (originOption != null) {
                        originOption.setName(optionDto.getName());
                        return originOption;
                    } else {
                        return new Option(optionDto.getName(), product);
                    }
                })
                .collect(Collectors.toList());
        product.setOptions(optionList);
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    public List<Option> getPtoductOptions(Long productId) {
        return optionService.getProductOptions(productId);
    }
}
