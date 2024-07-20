package gift.service;

import gift.entity.*;
import gift.exception.ResourceNotFoundException;
import gift.repository.OptionRepository;
import gift.repository.ProductOptionRepository;
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
    private final CategoryService categoryService;
    private final ProductOptionRepository productOptionRepository;
    private final OptionRepository optionRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductWishlistRepository productWishlistRepository, CategoryService categoryService, ProductOptionRepository productOptionRepository, OptionRepository optionRepository) {
        this.productRepository = productRepository;
        this.productWishlistRepository = productWishlistRepository;
        this.categoryService = categoryService;
        this.productOptionRepository = productOptionRepository;
        this.optionRepository = optionRepository;
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
        Category category = categoryService.findById(productDTO.getCategoryid());
        productDTO.setCategoryid(category.getId());
        Product product = productRepository.save(new Product(productDTO));

        Option defaultOption = optionRepository.findById(1L)
                .orElseThrow(() -> new ResourceNotFoundException("Option not found with id: 1L"));

        productOptionRepository.save(new ProductOption(product, defaultOption, defaultOption.getName()));

        return product;
    }

    public Product update(Long id, ProductDTO productDTO) {
        Product product = findById(id);
        Category category = categoryService.findById(productDTO.getCategoryid());
        productDTO.setCategoryid(category.getId());
        product.setProductWithCategory(productDTO);
        return productRepository.save(product);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    // option
    public List<Option> getOptions(Long id) {
        List<ProductOption> productOptions = productOptionRepository.findByProductId(id);
        return productOptions.stream()
                .map(productOption -> productOption.getOption())
                .collect(Collectors.toList());
    }

    public void addProductOption(Long productId, Long optionId) {
        Option option = optionRepository.findById(optionId)
                .orElseThrow(() -> new ResourceNotFoundException("Option not found with id: " + optionId));
        Product product = findById(productId);
        productOptionRepository.save(new ProductOption(product, option, option.getName()));
    }

    public void deleteProductOption(Long productId, Long optionId) {
        // 옵션이 하나 이상인지 확인 로직
        if (productOptionRepository.findByProductId(productId).size() <= 1) {
            throw new IllegalArgumentException("Option should have at least one product option");
        }

        productOptionRepository.deleteByProductIdAndOptionId(productId, optionId);
    }
}
