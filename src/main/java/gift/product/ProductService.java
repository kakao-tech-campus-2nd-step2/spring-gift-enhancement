package gift.product;

import gift.option.Option;
import java.util.List;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public HttpStatus createProduct(Product newProduct) {
        productRepository.save(newProduct);
        return HttpStatus.CREATED;
    }

    public HttpStatus updateProduct(Product changeProduct) throws NotFoundException {
        Product product = productRepository.findById(changeProduct.getId()).orElseThrow(
            NotFoundException::new);
        product.update(changeProduct.getName(),changeProduct.getPrice(),changeProduct.getImageUrl(),
            changeProduct.getCategoryId());
        productRepository.save(product);
      
        return HttpStatus.OK;
    }

    public HttpStatus deleteProduct(Long id) {
        productRepository.deleteById(id);

        return HttpStatus.OK;
    }

    public Product findById(Long productId) {
        return productRepository.findById(productId).orElseThrow();
    }

    public List<Option> addOption(Long productId, Option option){
        Product product = productRepository.findById(productId).orElseThrow();
        product.getOptions().add(option);

        return product.getOptions();
    }

    public List<Option> deleteOption(Long productId, Option option){
        Product product = productRepository.findById(productId).orElseThrow();
        product.getOptions().remove(option);

        return product.getOptions();
    }

}