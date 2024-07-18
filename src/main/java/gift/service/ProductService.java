package gift.service;

import gift.common.exception.EntityNotFoundException;
import gift.controller.dto.response.PagingResponse;
import gift.controller.dto.response.ProductResponse;
import gift.controller.dto.response.ProductWithOptionResponse;
import gift.model.Category;
import gift.model.Option;
import gift.model.Product;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import gift.service.dto.CreateProductDto;
import gift.service.dto.UpdateProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public PagingResponse<ProductResponse> findAllProductPaging(Pageable pageable) {
        Page<ProductResponse> pages = productRepository.findAllFetchJoin(pageable)
                .map(ProductResponse::from);
        return PagingResponse.from(pages);
    }

    @Transactional(readOnly = true)
    public ProductWithOptionResponse findById(Long id) {
        Product product = productRepository.findByIdFetchJoin(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Product with id " + id + " not found"));
        return ProductWithOptionResponse.from(product);
    }

    @Transactional
    public Long save(CreateProductDto request) {
        Option option = new Option(request.optionName(), request.optionQuantity());
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category with id " + request.categoryId() + " not found"));
        Product product = productRepository.save(new Product(request.name(), request.price(), request.imageUrl(), category, option));
        option.updateOptionByProduct(product);
        return product.getId();
    }

    @Transactional
    public void updateProduct(UpdateProductDto request) {
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
}
