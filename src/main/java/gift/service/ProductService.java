package gift.service;

import gift.entity.Category;
import gift.entity.Member;
import gift.entity.Option;
import gift.entity.Product;

import gift.exception.DataNotFoundException;
import gift.exception.DuplicateUserEmailException;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final int PAGE_SIZE = 5;

    public ProductService(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty()) {
            throw new DataNotFoundException("존재하지 않는 Product: Product를 찾을 수 없습니다.");
        }
        return product.get();
    }


    public void updateProduct(Product product, Long id) {

        Product update = getProductById(id);
        update.setName(product.getName());
        update.setPrice(product.getPrice());
        update.setImageUrl(product.getImageUrl());
        update.setCategory(product.getCategory());
        productRepository.save(update);


    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }


    public Page<Product> getProductPage(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc("id"));
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by(sorts));
        return productRepository.findAll(pageable);
    }


}
