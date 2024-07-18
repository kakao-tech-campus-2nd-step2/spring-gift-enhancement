package gift.product.service;

import gift.category.domain.Category;
import gift.category.repository.CategoryRepository;
import gift.category.service.CategoryService;
import gift.product.domain.Product;
import gift.product.domain.ProductDTO;
import gift.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository,
        CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
            .map(this::convertToDTO);
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
            .map(this::convertToDTO)
            .toList();
    }

    public Optional<ProductDTO> getProductDTOById(Long id) {
        return productRepository.findById(id)
            .map(this::convertToDTO);
    }

    public Optional<Product> getProductById(Long id){
        return productRepository.findById((id));
    }

    public void createProduct(@Valid ProductDTO productDTO) {
        productRepository.save(convertToEntity(productDTO));
    }

    public void updateProduct(Long id, @Valid ProductDTO productDTO) {
        if(productRepository.existsById(id)){
            Product product = updateById(id, productDTO);
            productRepository.save(product);
        }
    }

    private Product updateById(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Product id " + id + "가 없습니다."));
        //update 함수로
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setImageUrl(productDTO.getImageUrl());

        Category category = categoryRepository.findById(productDTO.getCategoryId())
            .orElseThrow(() -> new EntityNotFoundException("Category id " + productDTO.getCategoryId() + "가 없습니다."));
        product.setCategory(category);
        return product;
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
    private ProductDTO convertToDTO(Product product) {
        return new ProductDTO(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getImageUrl(),
            product.getCategory().getId()
        );
    }

    private Product convertToEntity(ProductDTO productDTO) {
        Category category = categoryRepository.findById(productDTO.getCategoryId()).get();

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setImageUrl(productDTO.getImageUrl());
        product.setCategory(category);

        return product;
    }
}
