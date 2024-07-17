package gift.service;

import gift.common.exception.DuplicateDataException;
import gift.common.exception.EntityNotFoundException;
import gift.controller.dto.request.CreateProductRequest;
import gift.controller.dto.request.UpdateProductRequest;
import gift.controller.dto.response.PagingResponse;
import gift.controller.dto.response.ProductResponse;
import gift.model.Category;
import gift.model.Option;
import gift.model.Product;
import gift.repository.CategoryRepository;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final OptionRepository optionRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, OptionRepository optionRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.optionRepository = optionRepository;
    }

    @Transactional(readOnly = true)
    public PagingResponse<ProductResponse> findAllProductPaging(Pageable pageable) {
        Page<ProductResponse> pages = productRepository.findAllFetchJoin(pageable)
                .map(ProductResponse::from);
        return PagingResponse.from(pages);
    }

    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        Product product = productRepository.findByIdFetchJoin(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Product with id " + id + " not found"));
        return ProductResponse.from(product);
    }

    @Transactional
    public Long save(CreateProductRequest request) {
        checkDuplicateOptionName(request.optionName());
        Option option = new Option(request.optionName(), request.optionQuantity());
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category with id " + request.categoryId() + " not found"));
        Product product = new Product(request.name(), request.price(), request.imageUrl(), category, option);
        return productRepository.save(product).getId();
    }

    @Transactional
    public void updateById(UpdateProductRequest request) {
        Product product = productRepository.findById(request.id())
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + request.id() + " not found"));
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category with name " + request.categoryId() + " not found"));
        product.updateProduct(request.name(), request.price(), request.imageUrl(), category);
    }

    @Transactional
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    private void checkDuplicateOptionName(String optionName) {
        if (optionRepository.existsByName(optionName)) {
            throw new DuplicateDataException("Option with name " + optionName + " already exists");
        }
    }
}
