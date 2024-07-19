package gift.services;

import gift.domain.Category;
import gift.domain.Option;
import gift.domain.Product;
import gift.dto.ProductDto;
import gift.dto.RequestOptionDto;
import gift.dto.RequestProductDto;
import gift.repositories.CategoryRepository;
import gift.repositories.OptionRepository;
import gift.repositories.ProductRepository;
import jakarta.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final OptionService optionService;
    private final ProductRepository productRepository;
    private final OptionRepository optionRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(OptionService optionService, ProductRepository productRepository,
        OptionRepository optionRepository, CategoryRepository categoryRepository) {
        this.optionService = optionService;
        this.productRepository = productRepository;
        this.optionRepository = optionRepository;
        this.categoryRepository = categoryRepository;
    }

    // 모든 제품 조회
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = products.stream().map(product -> new ProductDto(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getImageUrl(),
            product.getCategoryDto(),
            product.getOptionDtos()
        )).toList();

        return productDtos;
    }

    //Page 반환, 모든 제품 조회
    public Page<ProductDto> getAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        Page<ProductDto> productDtos = products.map(product -> new ProductDto(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getImageUrl(),
            product.getCategoryDto(),
            product.getOptionDtos()
        ));

        return productDtos;
    }

    // 특정 제품 조회
    public ProductDto getProductById(Long id) {

        Product product = productRepository.findById(id).orElseThrow(
            () -> new NoSuchElementException("Product not found with id " + id));

        ProductDto productDto = new ProductDto(product.getId(), product.getName(),
            product.getPrice(), product.getImageUrl(), product.getCategoryDto(), product.getOptionDtos());
        return productDto;
    }

    // 제품 추가
    public ProductDto addProduct(@Valid RequestProductDto requestProductDto) {

        Product product = new Product(
            requestProductDto.getName(),
            requestProductDto.getPrice(),
            requestProductDto.getImageUrl(),
            new Category(requestProductDto.getCategoryDto().getName(),
                requestProductDto.getCategoryDto().getColor(),
                requestProductDto.getCategoryDto().getImageUrl(),
                requestProductDto.getCategoryDto().getDescription())
        );

        for (RequestOptionDto requestOptionDto : requestProductDto.getOptionDtos()) {
            optionService.addOption(product.getId(), requestOptionDto);
        }
        List<Option> options = optionRepository.findAllByProductId(product.getId());
        product.setOptions(options);

        productRepository.save(product);

        return new ProductDto(product.getId(), product.getName(), product.getPrice(),
            product.getImageUrl(), product.getCategoryDto(), product.getOptionDtos());
    }

    // 제품 수정
    public ProductDto updateProduct(@Valid RequestProductDto requestProductDto) {
        Product product = productRepository.findById(requestProductDto.getId())
            .orElseThrow(() -> new NoSuchElementException(
                "Product not found with id " + requestProductDto.getId()));

        Category category = categoryRepository.findById(requestProductDto.getCategoryDto().getId())
            .orElseThrow(() -> new NoSuchElementException(
                "Category not found"));

        for (RequestOptionDto requestOptionDto : requestProductDto.getOptionDtos()) {
            optionService.addOption(product.getId(), requestOptionDto);
        }
        List<Option> options = optionRepository.findAllByProductId(product.getId());
        product.setOptions(options);

        product.update(requestProductDto.getName(), requestProductDto.getPrice(),
            requestProductDto.getImageUrl(), category, options);
        return new ProductDto(product.getId(), product.getName(), product.getPrice(),
            product.getImageUrl(), product.getCategoryDto(), product.getOptionDtos());
    }

    // 제품 삭제
    public void deleteProduct(Long id) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isEmpty()) {
            throw new NoSuchElementException("Product not found with id " + id);
        }

        productRepository.deleteById(id);
    }
}

