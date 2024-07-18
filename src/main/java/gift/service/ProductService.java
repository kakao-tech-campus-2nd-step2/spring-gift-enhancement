package gift.service;

import gift.domain.Category;
import gift.domain.Option;
import gift.domain.Product;
import gift.dto.OptionDto;
import gift.dto.ProductDto;
import gift.dto.request.OptionRequest;
import gift.exception.CategoryNotFoundException;
import gift.exception.DuplicateOptionException;
import gift.exception.OptionNotFoundException;
import gift.exception.ProductNotFoundException;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

        Set<String> optionNames = dto.getOptions().stream()
                .map(OptionRequest::getName)
                .collect(Collectors.toSet());

        if (optionNames.size() < dto.getOptions().size()) {
            throw new DuplicateOptionException();
        }

        dto.getOptions().stream()
                .map(optionRequest -> new Option(product, optionRequest.getName(), optionRequest.getQuantity()))
                .forEach(product.getOptions()::add);

        product.validateOptionsNotEmpty();

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

        boolean optionExists = product.getOptions().stream()
                .anyMatch(option -> option.getName().equals(dto.getName()));

        if (optionExists) {
            throw new DuplicateOptionException();
        }

        Option option = new Option(product, dto.getName(), dto.getQuantity());

        product.getOptions().add(option);
    }

    public void removeOption(Long productId, Long optionId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        List<Option> options = product.getOptions();

        boolean isRemoved = options.removeIf(option -> option.getId().equals(optionId));

        if (!isRemoved) {
            throw new OptionNotFoundException();
        }
    }

}
