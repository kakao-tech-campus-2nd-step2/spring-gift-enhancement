package gift.service;

import gift.domain.Category;
import gift.domain.Product;
import gift.exception.NoSuchCategoryException;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import gift.dto.ProductDTO;
import gift.exception.NoSuchProductException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Page<ProductDTO> getProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
            .map(product -> product.toDTO());
    }

    public ProductDTO getProduct(Long id) {
        return productRepository.findById(id)
            .orElseThrow(NoSuchProductException::new)
            .toDTO();
    }

    public ProductDTO addProduct(ProductDTO productDTO) {
        Category category = categoryRepository.findById(productDTO.categoryId())
            .orElseThrow(NoSuchCategoryException::new);
        return productRepository.save(productDTO.toEntity(category)).toDTO();
    }

    public ProductDTO updateProduct(long id, ProductDTO productDTO) {
        getProduct(id);
        Category category = categoryRepository.findById(productDTO.categoryId())
            .orElseThrow(NoSuchCategoryException::new);
        Product product = new Product(id, productDTO.name(), productDTO.price(), productDTO.imageUrl(), category);
        return productRepository.save(product).toDTO();
    }

    public ProductDTO deleteProduct(long id) {
        Product deletedProduct = productRepository.findById(id).orElseThrow(NoSuchProductException::new);
        productRepository.delete(deletedProduct);
        return deletedProduct.toDTO();
    }
}
