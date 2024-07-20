package gift.mapper;

import gift.domain.product.Product;
import gift.web.dto.ProductDto;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    private final CategoryMapper categoryMapper;

    public ProductMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    public ProductDto toDto(Product product) {
        return new ProductDto(product.getId(),
            product.getName(),
            product.getPrice(),
            product.getImageUrl(),
            categoryMapper.toDto(product.getCategory())
        );
    }

    public Product toEntity(ProductDto productDto) {
        return new Product(productDto.name(),
            productDto.price(),
            productDto.imageUrl(),
            categoryMapper.toEntity(productDto.categoryDto())
        );
    }
}
