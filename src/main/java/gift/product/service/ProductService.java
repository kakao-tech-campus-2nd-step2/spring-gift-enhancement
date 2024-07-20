package gift.product.service;

import gift.product.dto.ProductDTO;
import gift.product.exception.InvalidIdException;
import gift.product.model.Option;
import gift.product.model.Product;
import gift.product.repository.CategoryRepository;
import gift.product.repository.OptionRepository;
import gift.product.repository.ProductRepository;
import gift.product.validation.ProductValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static gift.product.exception.GlobalExceptionHandler.NOT_EXIST_ID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductValidation productValidation;
    private final CategoryRepository categoryRepository;
    private final OptionRepository optionRepository;

    @Autowired
    public ProductService(
        ProductRepository productRepository,
        ProductValidation productValidation,
        CategoryRepository categoryRepository,
        OptionRepository optionRepository) {
        this.productRepository = productRepository;
        this.productValidation = productValidation;
        this.categoryRepository = categoryRepository;
        this.optionRepository = optionRepository;
    }

    public void registerProduct(ProductDTO productDTO) {
        System.out.println("[ProductService] registerProduct()");
        productValidation.registerValidation(productDTO);

        Product product = productRepository.save(
            new Product(
                productDTO.getName(),
                productDTO.getPrice(),
                productDTO.getImageUrl(),
                categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new InvalidIdException(NOT_EXIST_ID))
            )
        );

        optionRepository.save(
            new Option(
                product.getName(),
                0,
                product
            )
        );
    }

    public void updateProduct(
            Long id,
            ProductDTO productDTO
    ) {
        System.out.println("[ProductService] updateProduct()");
        productValidation.updateValidation(id, productDTO);

        productRepository.save(
                new Product(
                        id,
                        productDTO.getName(),
                        productDTO.getPrice(),
                        productDTO.getImageUrl(),
                        categoryRepository.findById(productDTO.getCategoryId())
                                .orElseThrow(() -> new InvalidIdException(NOT_EXIST_ID))
                )
        );
    }

    public void deleteProduct(Long id) {
        productValidation.deleteValidation(id);
        if(optionRepository.existsByProductId(id))
            optionRepository.deleteByProductId(id);
        productRepository.deleteById(id);
    }

    public ProductDTO getDTOById(Long id) {
        System.out.println("[ProductService] getDTOById()");

        Product product = productRepository.findById(id)
            .orElseThrow(() -> new InvalidIdException(NOT_EXIST_ID));

        return convertToDTO(product);
    }

    public Page<ProductDTO> searchProducts(
            String keyword,
            Pageable pageable
    ) {
        return convertToDTOList(
            productRepository.findByName(
                keyword,
                pageable
            ),
            pageable
        );
    }

    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        return convertToDTOList(
            productRepository.findAll(pageable),
            pageable
        );
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new InvalidIdException(NOT_EXIST_ID));
    }

    public ProductDTO convertToDTO(Product product) {
        return new ProductDTO(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getImageUrl(),
            product.getCategory().getId()
        );
    }

    public Page<ProductDTO> convertToDTOList(Page<Product> products, Pageable pageable) {
        List<ProductDTO> productDTOs = products.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());

        return new PageImpl<>(
            productDTOs,
            pageable,
            products.getTotalElements()
        );
    }
}
