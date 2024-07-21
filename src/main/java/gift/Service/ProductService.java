package gift.Service;

import gift.Exception.CategoryNotFoundException;
import gift.Exception.ProductNotFoundException;
import gift.Model.*;
import gift.Repository.CategoryRepository;
import gift.Repository.OptionRepository;
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
    private final OptionRepository optionRepository;


    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, OptionRepository optionRepository) {
        this.productRepository = productRepository;
        this.categoryRepository  = categoryRepository;
        this.optionRepository = optionRepository;
    }

    @Transactional(readOnly = true)
    public Page<Product> getAllProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage;
    }

    @Transactional
    public void addProduct(RequestProductPost requestProductPost) {
        Category category = categoryRepository.findById(requestProductPost.categoryId())
                .orElseThrow(()-> new CategoryNotFoundException("매칭되는 카테고리가 없습니다"));
        Product product = new Product(requestProductPost.name(), requestProductPost.price(), requestProductPost.imageUrl(), category);
        productRepository.save(product);
        optionRepository.save(new Option(requestProductPost.name(), requestProductPost.optionQuantity(), product));
    }

    @Transactional(readOnly = true)
    public Product selectProduct(long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("매칭되는 product가 없습니다"));
        return product;
    }

    @Transactional
    public void editProduct(long id, RequestProduct requestProduct) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("매칭되는 product가 없습니다"));
        product.setName(requestProduct.name());
        product.setPrice(requestProduct.price());
        product.setImageUrl(requestProduct.imageUrl());
        Category category = categoryRepository.findById(requestProduct.categoryId())
                        .orElseThrow(()->new CategoryNotFoundException("매칭되는 카테고리가 없습니다"));
        product.setCategory(category);
    }

    @Transactional
    public void deleteProduct(long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("매칭되는 product가 없습니다"));
        optionRepository.deleteByProduct(product);
        productRepository.deleteById(product.getId());
    }


}
