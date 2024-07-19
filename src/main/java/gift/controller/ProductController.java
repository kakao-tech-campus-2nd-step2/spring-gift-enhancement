package gift.controller;

import gift.domain.Category;
import gift.domain.Option;
import gift.domain.Product;
import gift.dto.OptionDTO;
import gift.dto.ProductDTO;
import gift.service.CategoryService;
import gift.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Product> getProducts() {
        return productService.findAllProducts();
    }

    @GetMapping("/paged")
    public Page<Product> getProducts(Pageable pageable) {
        return productService.findAllProducts(pageable);
    }

    @PostMapping
    public Product postProduct(@Valid @RequestBody ProductDTO productDTO) {
        Category category = categoryService.getCategoryByName(productDTO.getCategoryName());
        Product product = productDTO.toEntity(category);
        return productService.createProduct(product);
    }

    @PutMapping("/{id}")
    public Product putProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {
        Category category = categoryService.getCategoryByName(productDTO.getCategoryName());
        Product product = new Product.ProductBuilder()
            .id(id)
            .name(productDTO.getName())
            .price(productDTO.getPrice())
            .imageUrl(productDTO.getImageUrl())
            .description(productDTO.getDescription())
            .category(category)
            .options(productDTO.getOptions().stream()
                .map(optionDTO -> optionDTO.toEntity())
                .collect(Collectors.toList()))
            .build();
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/{productId}/options")
    public List<Option> getProductOptions(@PathVariable Long productId) {
        Product product = productService.getProductById(productId);
        return product.getOptions();
    }

    @PostMapping("/{productId}/options")
    public Option addProductOption(@PathVariable Long productId, @Valid @RequestBody OptionDTO optionDTO) {
        Product product = productService.getProductById(productId);
        Option option = new Option.OptionBuilder()
            .name(optionDTO.getName())
            .quantity(optionDTO.getQuantity())
            .product(product)  // 옵션과 상품 간의 관계 설정
            .build();
        return productService.addOption(productId, option);  // 상품에 옵션 추가
    }
}
