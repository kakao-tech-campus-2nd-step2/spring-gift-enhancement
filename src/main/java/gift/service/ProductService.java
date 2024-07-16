package gift.service;

import gift.entity.*;
import gift.exception.ResourceNotFoundException;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import gift.repository.ProductWishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductWishlistRepository productWishlistRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductWishlistRepository productWishlistRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.productWishlistRepository = productWishlistRepository;
        this.categoryRepository = categoryRepository;
    }

    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    public List<Wishlist> getProductWishlist(Long productId) {
        List<ProductWishlist> productWishlists = productWishlistRepository.findByProductId(productId);
        List<Wishlist> wishlists = productWishlists.stream()
                .map(productWishlist -> productWishlist.getWishlist())
                .collect(Collectors.toList());
        return wishlists;
    }

    public Product save(ProductDTO productDTO) {
        Category category = categoryRepository.findById(productDTO.getCategoryid())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + productDTO.getCategoryid()));
        Product product = new Product(productDTO);
        product.setCategory(category);
        category.addProduct(product);
        return productRepository.save(product);
    }

    public Product update(Long id, ProductDTO productDTO) {
        Product product = findById(id);
        Category category = categoryRepository.findById(productDTO.getCategoryid())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        if (product.getCategory().getId() != productDTO.getCategoryid()) {
            category.removeProduct(product);
        }

        product.setName(product.getName());
        product.setPrice(product.getPrice());
        product.setImageurl(product.getImageurl());
        product.setCategory(category);

        categoryRepository.save(category);

        return productRepository.save(product);
    }

    public void delete(Long id) {
        Product product = findById(id);
        productRepository.delete(product);
    }
}
