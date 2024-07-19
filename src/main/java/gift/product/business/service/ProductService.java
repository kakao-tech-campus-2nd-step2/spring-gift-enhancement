package gift.product.business.service;

import gift.product.business.dto.OptionRegisterDto;
import gift.product.business.dto.ProductPagingDto;
import gift.product.persistence.entity.Product;
import gift.product.persistence.repository.CategoryRepository;
import gift.product.persistence.repository.ProductRepository;
import gift.product.business.dto.ProductDto;
import gift.product.business.dto.ProductRegisterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public ProductDto getProduct(Long id) {
        Product product = productRepository.getProductById(id);
        return ProductDto.from(product);
    }

    @Transactional
    public Long createProduct(ProductRegisterDto productRegisterDto, List<OptionRegisterDto> optionRegisterDtos) {
        var category = categoryRepository.getReferencedCategory(productRegisterDto.categoryId());
        Product product = productRegisterDto.toProduct(category);
        var productId = productRepository.saveProduct(product);
        optionService.createOption(optionRegisterDtos, productId);
        return productId;
    }

    @Transactional
    public Long updateProduct(ProductRegisterDto productRegisterDto, Long id) {
        var product = productRepository.getProductById(id);
        var category = categoryRepository.getReferencedCategory(productRegisterDto.categoryId());
        product.update(productRegisterDto.name(), productRegisterDto.description(),
                productRegisterDto.price(), productRegisterDto.url());
        product.updateCategory(category);
        return productRepository.saveProduct(product);
    }

    @Transactional
    public Long deleteProduct(Long id) {
        return productRepository.deleteProductById(id);
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.getAllProducts();
        return ProductDto.of(products);
    }

    @Transactional
    public void deleteProducts(List<Long> productIds) {
        productRepository.deleteProductByIdList(productIds);
    }

    @Transactional(readOnly = true)
    public ProductPagingDto getProductsByPage(Pageable pageable) {
        Page<Product> products = productRepository.getProductsByPage(pageable);
        return ProductPagingDto.from(products);
    }
}
