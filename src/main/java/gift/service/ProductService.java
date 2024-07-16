package gift.service;

import gift.constants.Messages;
import gift.domain.Product;
import gift.dto.request.ProductRequest;
import gift.dto.response.ProductResponse;
import gift.exception.ProductNotFoundException;
import gift.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Transactional
    public void save(ProductRequest productDto){
        productRepository.save(productDto.toEntity());
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
    public void updateById(Long id, ProductRequest productDto){
        findProductByIdOrThrow(id);
        productRepository.save(new Product.Builder()
                .id(id)
                .name(productDto.name())
                .price(productDto.price())
                .imageUrl(productDto.imageUrl())
                .build());
    }

    private Product findProductByIdOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(Messages.NOT_FOUND_PRODUCT_BY_ID));
    }
}
