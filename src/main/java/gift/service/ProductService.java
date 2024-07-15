package gift.service;

import gift.exception.ErrorCode;
import gift.exception.RepositoryException;
import gift.model.Category;
import gift.model.Product;
import gift.model.ProductDTO;
import gift.model.ProductPageDTO;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository,
        CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProductDTO createProduct(ProductDTO productDTO) {
        Category category = categoryRepository.findById(productDTO.categoryId()).orElseThrow(
            () -> new RepositoryException(ErrorCode.CATEGORY_NOT_FOUND, productDTO.categoryId()));
        Product product = new Product(productDTO.id(), productDTO.name(), productDTO.price(),
            productDTO.imageUrl(), category);
        return convertToDTO(productRepository.save(product));
    }

    public ProductPageDTO getAllProduct(int pageNum, int size) {
        Pageable pageable = PageRequest.of(pageNum, size);
        List<ProductDTO> productPage = productRepository.findAll(pageable)
            .map(this::convertToDTO)
            .stream()
            .toList();

        return new ProductPageDTO(pageNum, size, productPage.size(), productPage);
    }

    public List<ProductDTO> getAllProductByList() {
        List<Product> products = productRepository.findAll();
        return products.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public ProductDTO getProductById(long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new RepositoryException(ErrorCode.PRODUCT_NOT_FOUND, ""));
        return convertToDTO(product);
    }

    public ProductDTO updateProduct(long id, ProductDTO productDTO) {
        Category category = categoryRepository.findById(productDTO.categoryId()).orElseThrow(
            () -> new RepositoryException(ErrorCode.CATEGORY_NOT_FOUND, productDTO.categoryId()));
        Product product = new Product(id, productDTO.name(), productDTO.price(),
            productDTO.imageUrl(), category);
        return convertToDTO(productRepository.save(product));
    }

    public String deleteProduct(long id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new RepositoryException(ErrorCode.PRODUCT_NOT_FOUND, "");
        }
        return "성공적으로 삭제되었습니다.";
    }

    private ProductDTO convertToDTO(Product product) {
        return new ProductDTO(product.getId(), product.getName(), product.getPrice(),
            product.getImageUrl(), product.getCategory().getId());
    }
}
