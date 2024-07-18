package gift.service;

import gift.constants.Messages;
import gift.domain.Category;
import gift.domain.Product;
import gift.dto.request.ProductRequest;
import gift.dto.response.ProductResponse;
import gift.exception.ProductNotFoundException;
import gift.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public ProductService(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @Transactional
    public void save(ProductRequest productRequest){
        Category category = categoryService.findById(productRequest.categoryId()).toEntity();
        productRepository.save(productRequest.toEntity(category));
    }

    @Transactional(readOnly = true)
    public ProductResponse findById(Long id){
         Product product = findProductByIdOrThrow(id);
         return ProductResponse.from(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> getPagedProducts(Pageable pageable){
        Page<Product> pagedProduct = productRepository.findAll(pageable);
        return pagedProduct.map(ProductResponse::from);
    }

    @Transactional(readOnly = true)
    public ProductResponse findByName(String name){
        Product product =  productRepository.findByName(name)
                .orElseThrow(()->  new ProductNotFoundException(Messages.NOT_FOUND_PRODUCT_BY_NAME));
        return ProductResponse.from(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll(){
        return productRepository.findAll()
                .stream()
                .map(ProductResponse::from)
                .toList();
    }

    @Transactional
    public void deleteById(Long id){
        findProductByIdOrThrow(id);
        productRepository.deleteById(id);
    }

    @Transactional
    public void updateById(Long id, ProductRequest productRequest){
        Product foundProduct = findProductByIdOrThrow(id);
        Category category = categoryService.findById(productRequest.categoryId()).toEntity();

        foundProduct.updateProduct(productRequest.name(), productRequest.price(), productRequest.imageUrl(),category);
        productRepository.save(foundProduct);
    }

    private Product findProductByIdOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(Messages.NOT_FOUND_PRODUCT_BY_ID));
    }
}
