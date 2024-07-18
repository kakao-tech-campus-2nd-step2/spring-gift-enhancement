package gift.service;

import gift.domain.Category;
import gift.domain.Option;
import gift.domain.Product;
import gift.dto.OptionDto;
import gift.dto.ProductDto;
import gift.exception.CategoryNotFoundException;
import gift.exception.ProductNotFoundException;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable).stream()
                .map(Product::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductDto getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new)
                .toDto();
    }

    public void addProduct(ProductDto dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(CategoryNotFoundException::new);

        Product product = new Product(dto.getName(), dto.getPrice(), dto.getImageUrl(), category);
        List<Option> options = dto.getOptions().stream()
                .map(OptionDto::toEntity)
                .toList();

        options.forEach(product::addOption);

        productRepository.save(product);
    }

    public void editProduct(Long productId, ProductDto dto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(CategoryNotFoundException::new);

        product.changeName(dto.getName());
        product.changePrice(dto.getPrice());
        product.changeImageUrl(dto.getImageUrl());
        product.changeCategory(category);
    }

    public void removeProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        productRepository.deleteById(product.getId());
    }

    @Transactional(readOnly = true)
    public List<OptionDto> getOptions(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        return product.getOptions().stream()
                .map(Option::toDto)
                .toList();
    }

    public void addOption(Long productId, OptionDto dto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        Option option = new Option(product, dto.getName(), dto.getQuantity());

        product.validateOptionNameUnique(option.getName());
        product.addOption(option);
    }

    public void removeOption(Long productId, Long optionId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        product.removeOptionById(optionId);
    }

}
