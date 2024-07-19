package gift.service;

import gift.domain.Category;
import gift.domain.Product;
import gift.dto.requestDto.ProductRequestDTO;
import gift.dto.responseDto.ProductResponseDTO;
import gift.repository.JpaCategoryRepository;
import gift.repository.JpaProductRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ProductService {
    private final JpaProductRepository jpaProductRepository;
    private final JpaCategoryRepository jpaCategoryRepository;

    public ProductService(JpaProductRepository jpaProductRepository,
        JpaCategoryRepository jpaCategoryRepository) {
        this.jpaProductRepository = jpaProductRepository;
        this.jpaCategoryRepository = jpaCategoryRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getAllProducts() {

        return jpaProductRepository.findAll()
            .stream()
            .map(ProductResponseDTO::of)
            .toList();
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getAllProducts(int page, int size, String criteria) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(criteria));

        return jpaProductRepository.findAll(pageable)
            .stream()
            .map(ProductResponseDTO::of)
            .toList();
    }

    @Transactional(readOnly = true)
    public ProductResponseDTO getOneProduct(Long productId) {
        Product product = getProduct(productId);
        return ProductResponseDTO.of(product);
    }

    public Long addProduct(ProductRequestDTO productRequestDTO) {
        Category category = getCategory(productRequestDTO);

        Product product = new Product(productRequestDTO.name(), productRequestDTO.price(),
            productRequestDTO.imageUrl(), category);

        return jpaProductRepository.save(product).getId();
    }

    public Long updateProduct(Long productId, ProductRequestDTO productRequestDTO) {
        Product product = getProduct(productId);
        Category category = getCategory(productRequestDTO);

        product.update(productRequestDTO.name(), productRequestDTO.price(),
            productRequestDTO.imageUrl(), category);
        return product.getId();
    }

    public Long deleteProduct(Long productId) {
        Product product = getProduct(productId);
        jpaProductRepository.delete(product);
        return product.getId();
    }

    private Product getProduct(Long productId) {
        return jpaProductRepository.findById(productId)
            .orElseThrow(() -> new NoSuchElementException("id가 잘못되었습니다."));
    }

    private Category getCategory(ProductRequestDTO productRequestDTO) {
        return jpaCategoryRepository.findById(productRequestDTO.categoryId())
            .orElseThrow(() -> new NoSuchElementException("id가 잘못되었습니다."));
    }
}
