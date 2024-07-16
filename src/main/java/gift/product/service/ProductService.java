package gift.product.service;

import gift.category.model.Category;
import gift.category.repository.CategoryRepository;
import gift.common.exception.ProductAlreadyExistsException;
import gift.product.model.Product;
import gift.product.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAllWithCategory();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Transactional
    public void createProduct(Product product, Long CategoryId) {
        // 영속화된 category
        Category category = categoryRepository.findById(CategoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category with id " + CategoryId + " not found"));

        // product에 category 설정
        product.setCategory(category);

        // 양방향 설정
        category.addProduct(product);

        // validate
        checkForDuplicateProduct(product);

        // 저장 (category에도 변동사항 반영됨)
        productRepository.save(product);
    }

    public void updateProduct(Long id, @Valid Product product) {
        checkForDuplicateProduct(product);

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product with id " + id + " not found"));
        existingProduct.setId(id);
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setImageUrl(product.getImageUrl());

        productRepository.save(existingProduct);
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product with id " + id + " not found"));
        productRepository.delete(existingProduct);
    }

    public void checkForDuplicateProduct(Product product) {
        List<Product> products = productRepository.findAll();
        for (Product p : products) {
            if (p.equals(product)) {
                throw new ProductAlreadyExistsException(product.getName());
            }
        }
    }

    // 페이지네이션 기능 추가
    @Transactional(readOnly = true)
    public Page<Product> getProductsByPage(int page, int size, String sortBy, String direction) {
        // validation
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Invalid page or size");
        }

        // sorting
        Sort sort;
        if (direction.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }

        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findAll(pageable);
    }

    // 카테고리 목록을 반환하는 메서드 추가
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
