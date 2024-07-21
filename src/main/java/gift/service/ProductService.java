package gift.service;

import gift.dto.ProductRequestDTO;
import gift.dto.ProductResponseDTO;
import gift.entity.Category;
import gift.entity.Product;
import gift.exception.CategoryException;
import gift.exception.ProductException;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Product getProduct(long productId) {
        Optional<Product> product = productRepository.findById(productId);
        return product.orElseThrow(() -> new ProductException("상품을 찾을 수 없습니다."));
    }

    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductService::toDto)
                .collect(Collectors.toList());
    }


    public void saveProduct(ProductRequestDTO productRequestDTO){

        String categoryName = productRequestDTO.category();
        Optional<Category> existingCategory = categoryRepository.findByName(categoryName);
        existingCategory.orElseThrow(() -> new CategoryException("카테고리를 찾을 수 없습니다. 먼저 카테고리를 등록해주세요.") );

        Product product = toEntity(productRequestDTO, existingCategory.get());
        productRepository.save(product);

    }

    public void deleteProduct(Long productId) {
        Optional<Product> existingProduct = productRepository.findById(productId);
        existingProduct.orElseThrow(() -> new ProductException("상품을 찾을 수 없어서 삭제할 수 없습니다."));
        productRepository.deleteById(productId);
    }

    @Transactional
    public void updateProduct(Long productId, ProductRequestDTO productRequestDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException("상품을 찾을 수 없어서 업데이트 할 수 없습니다."));
        product.setName(productRequestDTO.name());
        product.setPrice(productRequestDTO.price());
        product.setImageUrl(productRequestDTO.imageUrl());
        productRepository.save(product);
    }

    public static Product toEntity(ProductRequestDTO dto, Category category) {
        Product product = new Product();
        product.setName(dto.name());
        product.setPrice(dto.price());
        product.setImageUrl(dto.imageUrl());
        product.setCategory(category);
        return product;
    }

    private static ProductResponseDTO toDto(Product product){
        return new ProductResponseDTO(product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl(),
                product.getCategory()
                        .getName());
    }

}
