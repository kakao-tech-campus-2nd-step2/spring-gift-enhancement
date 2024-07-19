package gift.service;

import gift.domain.Option;
import gift.domain.Product;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import gift.util.AlphanumericComparator;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.Comparator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final OptionRepository optionRepository;

    public ProductService(ProductRepository productRepository, OptionRepository optionRepository) {
        this.productRepository = productRepository;
        this.optionRepository = optionRepository;  // OptionRepository 초기화
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Page<Product> findAllProducts(Pageable pageable) {
        List<Product> products = getSortedProducts();

        return paginate(products, pageable);
    }

    private List<Product> getSortedProducts() {
        List<Product> products = productRepository.findAll();
        products.sort(Comparator.comparing(Product::getName, new AlphanumericComparator()));
        return products;
    }

    private Page<Product> paginate(List<Product> products, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Product> paginatedList = getPaginatedList(products, startItem, pageSize);

        return new PageImpl<>(paginatedList, pageable, products.size());
    }

    private List<Product> getPaginatedList(List<Product> products, int startItem, int pageSize) {
        if (products.size() <= startItem) {
            return Collections.emptyList();
        }

        int toIndex = Math.min(startItem + pageSize, products.size());
        return products.subList(startItem, toIndex);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {
        if (!isExist(id)) {
            return createProduct(product);
        }
        Product updatedProduct = new Product.ProductBuilder()
            .id(id)
            .name(product.getName())
            .price(product.getPrice())
            .imageUrl(product.getImageUrl())
            .description(product.getDescription())
            .options(product.getOptions())  // 옵션 리스트 추가
            .build();
        return productRepository.save(updatedProduct);
    }

    public void deleteProduct(Long id) {
        if (!isExist(id)) {
            throw new IllegalArgumentException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
    }

    private boolean isExist(Long id) {
        return productRepository.existsById(id);
    }

    @Transactional
    public Option addOption(Long productId, Option option) {
        Product product = getProductById(productId);
        option = new Option.OptionBuilder()
            .name(option.getName())
            .quantity(option.getQuantity())
            .product(product)  // Product 설정
            .build();
        return optionRepository.save(option);  // Option 저장
    }
}
