package gift.product.service;

import gift.exception.CustomException;
import gift.exception.ErrorCode;
import gift.product.category.entity.Category;
import gift.product.category.repository.CategoryRepository;
import gift.product.dto.request.CreateProductRequest;
import gift.product.dto.request.NewOption;
import gift.product.dto.request.UpdateProductRequest;
import gift.product.dto.response.ProductResponse;
import gift.product.entity.Product;
import gift.product.option.dto.request.CreateOptionRequest;
import gift.product.option.service.OptionService;
import gift.product.repository.ProductRepository;
import gift.util.mapper.ProductMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final OptionService optionService;

    public ProductService(ProductRepository productRepository,
        CategoryRepository categoryRepository, OptionService optionService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.optionService = optionService;
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(ProductMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
        return ProductMapper.toResponse(product);
    }

    @Transactional
    public Long createProduct(CreateProductRequest request) {
        Category category = categoryRepository.findById(request.categoryId())
            .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));

        Product product = productRepository.save(ProductMapper.toProduct(request, category));

        for (NewOption option : request.options()) {
            CreateOptionRequest optionRequest = new CreateOptionRequest(product.getId(),
                option.name(), option.quantity());
            optionService.createOption(optionRequest);
        }

        return product.getId();
    }

    @Transactional
    public void updateProduct(Long id, UpdateProductRequest request) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
        Category category = categoryRepository.findById(request.categoryId())
            .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));

        product.edit(request, category);
    }

    @Transactional
    public void deleteProduct(Long id) {
        checkProductExist(id);
        productRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public void checkProductExist(Long id) {
        if (!productRepository.existsById(id)) {
            throw new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
        }
    }
}
