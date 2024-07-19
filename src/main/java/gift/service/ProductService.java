package gift.service;

import gift.domain.Category;
import gift.dto.ProductRegisterRequestDto;
import gift.domain.Product;
import gift.dto.ProductResponseDto;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.data.domain.Page;
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

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public ProductResponseDto getProductById(long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("해당 id의 상품 없음: " + id));;
        return new ProductResponseDto(product.getName(), product.getPrice(), product.getImageUrl(), product.getCategory().getName());
    }

    public Long addProduct(ProductRegisterRequestDto productDto){
        Category category = categoryRepository.findByName(productDto.getCategoryName());
        Product newProduct = new Product(productDto.getName(),productDto.getPrice(),productDto.getImageUrl(), category);

//        // 상품에는 항상 하나 이상의 옵션이 있어야 한다.
//        Option defaultOption = new Option("default option", 1, newProduct);
//        newProduct.addOption(defaultOption);

        Product savedProduct = productRepository.save(newProduct);
        return savedProduct.getId();
    }

    public Long updateProduct(long id, ProductRegisterRequestDto productDto){
        Product existingProduct = productRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("해당 id의 상품 없음: " + id));

        existingProduct.setName(productDto.getName());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setImageUrl(productDto.getImageUrl());
        existingProduct.updateCategory(categoryRepository.findByName(productDto.getCategoryName()));

        Product savedProduct = productRepository.save(existingProduct);
        return savedProduct.getId();
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Page<ProductResponseDto> getPagedProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map(ProductResponseDto::convertToDto);
    }


}
