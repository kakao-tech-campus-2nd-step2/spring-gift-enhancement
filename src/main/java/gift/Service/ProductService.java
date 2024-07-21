package gift.Service;

import gift.Model.Category;
import gift.Model.Product;
import gift.Repository.CategoryRepository;
import gift.Repository.OptionRepository;
import gift.Repository.ProductRepository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final OptionRepository optionRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, OptionRepository optionRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.optionRepository = optionRepository;
    }

    public Page<Product> findAll(Pageable pageable){
        return productRepository.findAll(pageable);
    }

    public Product getProductById(Long id){
        return productRepository.findProductById(id);
    }

    public void addProduct(Product product){
        productRepository.save(product);
    }

    public void updateProduct(Product product){
        productRepository.save(product);
    }

    public void deleteProduct(Long id){
        optionRepository.deleteByProductId(id);
        productRepository.deleteById(id);
    }

    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }
}
