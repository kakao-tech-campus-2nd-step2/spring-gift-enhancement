package gift.product.service;

import gift.category.model.Category;
import gift.category.repository.CategoryRepository;
import gift.product.dto.ProductDto;
import gift.product.model.Product;
import gift.product.model.ProductName;
import gift.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public void save(ProductDto productDto) {
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("카테고리를 찾을 수 없습니다."));

        ProductName productName = new ProductName(productDto.getName());
        Product product = new Product(productName, productDto.getPrice(), productDto.getImgUrl(), category);
        productRepository.save(product);
    }

    @Transactional
    public void update(Long productId, ProductDto productDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("카테고리를 찾을 수 없습니다."));

        ProductName productName = new ProductName(productDto.getName());
        product.update(productName, productDto.getPrice(), productDto.getImgUrl(), category);
        productRepository.save(product);
    }

    @Transactional
    public void deleteById(Long productId) {
        productRepository.deleteById(productId);
    }
}