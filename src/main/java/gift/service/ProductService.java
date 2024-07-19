package gift.service;

import gift.dto.CategoryDTO;
import gift.dto.ProductDTO;
import gift.model.Category;
import gift.model.Product;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import gift.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final WishlistRepository wishlistRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, WishlistRepository wishlistRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.wishlistRepository = wishlistRepository;
    }

    public Page<ProductDTO> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(ProductDTO::new);
    }

    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        return new ProductDTO(product);
    }

    public void saveProduct(ProductDTO productDTO) {
        CategoryDTO categoryDTO = productDTO.getCategory();
        if (categoryDTO == null || categoryDTO.getName() == null) {
            throw new IllegalArgumentException("Category must not be null");
        }

        Category category = categoryRepository.findByName(categoryDTO.getName());
        if (category == null) {
            throw new IllegalArgumentException("Invalid category name: " + categoryDTO.getName());
        }

        Product product = new Product(null, productDTO.getName(), productDTO.getPrice(), productDTO.getImageUrl(), category);
        productRepository.save(product);
    }

    public void updateProduct(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        Category category = categoryRepository.findByName(productDTO.getCategory().getName());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setImageUrl(productDTO.getImageUrl());
        product.setCategory(category);
        productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        wishlistRepository.deleteByProductId(id);
        productRepository.deleteById(id);
    }
}