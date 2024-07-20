package gift.service;

import gift.dto.request.AddProductRequest;
import gift.dto.request.OptionRequest;
import gift.dto.request.UpdateProductRequest;
import gift.dto.response.AddedOptionIdResponse;
import gift.dto.response.AddedProductIdResponse;
import gift.dto.response.OptionResponse;
import gift.dto.response.ProductResponse;
import gift.entity.Category;
import gift.entity.Option;
import gift.entity.Product;
import gift.exception.ProductNotFoundException;
import gift.repository.ProductRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final OptionService optionService;

    public ProductService(ProductRepository productRepository, CategoryService categoryService, OptionService optionService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.optionService = optionService;
    }

    @Transactional
    public AddedProductIdResponse addProduct(AddProductRequest request) {
        Category category = categoryService.getCategory(request.categoryId());
        List<Option> options = optionService.convertToOptions(request.optionRequests());

        Product product = new Product(request, category, options);

        Long addedProductId = productRepository.save(product).getId();
        return new AddedProductIdResponse(addedProductId);
    }

    public Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
    }

    public Page<ProductResponse> getProductResponses(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(ProductResponse::fromProduct);
    }

    @Transactional
    public void updateProduct(UpdateProductRequest request) {
        Product product = productRepository.findById(request.id())
                .orElseThrow(ProductNotFoundException::new);

        Category category = categoryService.getCategory(request.categoryId());

        product.update(request.name(), request.price(), request.imageUrl(), category);
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
        productRepository.delete(product);
    }

    @Transactional
    public void deleteProducts(List<Long> ids) {
        productRepository.deleteAllById(ids);
    }

    public List<OptionResponse> getOptionResponses(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new)
                .getOptions()
                .stream()
                .map(OptionResponse::fromOption)
                .toList();
    }

    public AddedOptionIdResponse addOptionToProduct(Long productId, OptionRequest optionRequest) {
        Product product = getProduct(productId);

        optionService.checkDuplicateOptionName(product.getOptions(), optionRequest.name());

        Option option = optionService.convertToOption(optionRequest);
        product.addOption(option);
        productRepository.flush();

        return new AddedOptionIdResponse(option.getId());
    }

    @Cacheable (cacheNames = "options")
    @Transactional
    public List<String> getOptionNames(Long productId) {
        Product product = getProduct(productId);
        return product.getOptions()
                .stream()
                .map(Option::getName)
                .toList();
    }
}
