package gift.Service;

import gift.Model.Category;
import gift.Model.Product;
import gift.Model.RequestProduct;
import gift.Repository.CategoryRepository;
import gift.Repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository  = categoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<Product> getAllProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage;
    }

    @Transactional
    public void addProduct(RequestProduct requestProduct) {
        Category category = categoryRepository.findById(requestProduct.categoryId())
                .orElseThrow(()-> new NoSuchElementException("매칭되는 카테고리가 없습니다"));
        Product product = new Product(requestProduct.name(), requestProduct.price(), requestProduct.imageUrl(), category);
        productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public Product selectProduct(long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NoSuchElementException("매칭되는 product가 없습니다"));
        return product;
    }

    @Transactional
    public void editProduct(long id, RequestProduct requestProduct) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NoSuchElementException("매칭되는 product가 없습니다"));
        product.setName(requestProduct.name());
        product.setPrice(requestProduct.price());
        product.setImageUrl(requestProduct.imageUrl());
        Category category = categoryRepository.findById(requestProduct.categoryId())
                        .orElseThrow(()->new NoSuchElementException("매칭되는 카테고리가 없습니다"));
        product.setCategory(category);
    }

    @Transactional
    public void deleteProduct(long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NoSuchElementException("매칭되는 product가 없습니다"));
        productRepository.deleteById(product.getId());
    }


}
