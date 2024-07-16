package gift.service;

import gift.dto.ProductRequestDTO;
import gift.dto.ProductsPageResponseDTO;
import gift.model.Category;
import gift.model.Product;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public void createProduct(ProductRequestDTO productRequestDTO) {
        Category category = categoryRepository.findById(productRequestDTO.categoryId()).orElse(null);
        Product product = new Product(productRequestDTO.name(),
                                      productRequestDTO.price(),
                                      productRequestDTO.imageUrl(),
                                      category);

        productRepository.save(product);
    }

    public ProductsPageResponseDTO getAllProducts(Pageable pageable) {
        Page<Product> pages = productRepository.findAll(pageable);

        return new ProductsPageResponseDTO(pages.getContent(),
                                           pages.getNumber(),
                                           pages.getTotalPages());
    }


    public void updateProduct(Long id, ProductRequestDTO productRequestDTO) {
        Category category = categoryRepository.findById(productRequestDTO.categoryId()).orElse(null);
        productRepository.findById(id).ifPresent(product -> {
            Product updatedProduct = new Product(id,
                                                 productRequestDTO.name(),
                                                 productRequestDTO.price(),
                                                 productRequestDTO.imageUrl(),
                                                 category);

            productRepository.save(updatedProduct);
        });
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
