package gift.service;

import gift.domain.Category;
import gift.domain.Option;
import gift.domain.Product;
import gift.exception.CategoryNotFoundException;
import gift.exception.OptionAlreadyExistsException;
import gift.exception.ProductNotFoundException;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import gift.request.OptionRequest;
import gift.request.ProductRequest;
import gift.response.OptionResponse;
import gift.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
    public Page<ProductResponse> getProducts(Pageable pageable) {
        List<ProductResponse> response = productRepository.findAll(pageable).stream()
                .map(Product::toDto)
                .toList();

        return new PageImpl<>(response, pageable, response.size());
    }

    @Transactional(readOnly = true)
    public ProductResponse getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new)
                .toDto();
    }

    public void addProduct(ProductRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(CategoryNotFoundException::new);
        Product product = new Product(request.getName(), request.getPrice(), request.getImageUrl(), category);

        productRepository.save(product);
    }

    public void editProduct(Long productId, ProductRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(CategoryNotFoundException::new);

        product.changeName(request.getName());
        product.changePrice(request.getPrice());
        product.changeImageUrl(request.getImageUrl());
        product.changeCategory(category);
    }

    public void removeProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        productRepository.deleteById(product.getId());
    }

    @Transactional(readOnly = true)
    public List<OptionResponse> getOptions(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        return product.getOptions().stream()
                .map(Option::toDto)
                .toList();
    }

    public void addOption(Long productId, OptionRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);

        boolean optionExists = product.getOptions().stream()
                .anyMatch(option -> option.getName().equals(request.getName()));

        if (optionExists) {
            throw new OptionAlreadyExistsException();
        }

        Option option = new Option(product, request.getName(), request.getQuantity());

        product.getOptions().add(option);
    }

}
