package gift.product.service;

import gift.category.repository.CategoryRepository;
import gift.product.dto.ProductReqDto;
import gift.product.dto.ProductResDto;
import gift.product.entity.Product;
import gift.product.exception.ProductCreateException;
import gift.product.exception.ProductDeleteException;
import gift.product.exception.ProductNotFoundException;
import gift.product.exception.ProductUpdateException;
import gift.product.repository.ProductRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public Page<ProductResDto> getProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);

        List<ProductResDto> productResDtos = products.stream()
                .map(ProductResDto::new)
                .toList();

        return new PageImpl<>(productResDtos, pageable, products.getTotalElements());
    }

    @Transactional(readOnly = true)
    public ProductResDto getProduct(Long productId) {
        Product findProduct = findProductByIdOrThrow(productId);
        return new ProductResDto(findProduct);
    }

    @Transactional
    public ProductResDto addProduct(ProductReqDto productReqDto) {
        Product newProduct;
        try {
            newProduct = productRepository.save(productReqDto.toEntity());
            categoryRepository.findByName(productReqDto.category())
                    .ifPresent(newProduct::changeCategory);
        } catch (Exception e) {
            throw ProductCreateException.EXCEPTION;
        }

        return new ProductResDto(newProduct);
    }

    @Transactional
    public void updateProduct(Long productId, ProductReqDto productReqDto) {
        Product findProduct = findProductByIdOrThrow(productId);
        try {
            findProduct.update(productReqDto);
            categoryRepository.findByName(productReqDto.category())
                    .ifPresent(findProduct::changeCategory);
        } catch (Exception e) {
            throw ProductUpdateException.EXCEPTION;
        }
    }

    @Transactional
    public void deleteProduct(Long productId) {
        Product findProduct = findProductByIdOrThrow(productId);
        try {
            productRepository.delete(findProduct);
        } catch (Exception e) {
            throw ProductDeleteException.EXCEPTION;
        }
    }


    private Product findProductByIdOrThrow(Long productId) {
        return productRepository.findById(productId).orElseThrow(
                () -> ProductNotFoundException.EXCEPTION
        );
    }
}
