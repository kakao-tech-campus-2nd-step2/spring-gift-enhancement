package gift.category;

import gift.exception.AlreadyExistCategory;
import gift.exception.InvalidCategory;
import gift.product.Product;
import gift.product.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public List<CategoryResponseDto> getAllCategory() {
        return categoryRepository.findAll().stream()
            .map(category -> new CategoryResponseDto(
                category.getId(),
                category.getName(),
                category.getColor(),
                category.getImageUrl(),
                category.getDescription()))
            .collect(Collectors.toList());
    }

    public CategoryResponseDto postCategory(CategoryRequestDto categoryRequestDto) {
        Cateogory cateogory = new Cateogory(
            categoryRequestDto.name(),
            categoryRequestDto.color(),
            categoryRequestDto.imageUrl(),
            categoryRequestDto.description()
        );
        if (categoryRepository.findByName(cateogory.getName()).isPresent()) {
            throw new AlreadyExistCategory("동일한 카테고리가 이미 존재합니다.");
        }

        categoryRepository.saveAndFlush(cateogory);

        return new CategoryResponseDto(
            cateogory.getId(),
            cateogory.getName(),
            cateogory.getColor(),
            cateogory.getImageUrl(),
            cateogory.getDescription()
        );
    }

    public CategoryResponseDto putCategory(Long id, CategoryRequestDto categoryRequestDto) {
        Cateogory cateogory = categoryRepository.findById(id)
            .orElseThrow(() -> new InvalidCategory("유효하지 않은 카테고리입니다."));

        cateogory.update(categoryRequestDto.name(), categoryRequestDto.color(), categoryRequestDto.imageUrl(), categoryRequestDto.description());
        categoryRepository.saveAndFlush(cateogory);

        return new CategoryResponseDto(
            cateogory.getId(),
            cateogory.getName(),
            cateogory.getColor(),
            cateogory.getImageUrl(),
            cateogory.getDescription()
        );
    }

    public HttpEntity<String> deleteCategoryById(Long id) {
        Cateogory cateogory = categoryRepository.findById(id)
            .orElseThrow(() -> new InvalidCategory("유효하지 않은 카테고리입니다."));

        categoryRepository.delete(cateogory);

        return ResponseEntity.ok("성공적으로 삭제되었습니다");
    }

    public CategoryResponseDto getCategoryById(Long id) {
        Cateogory cateogory = categoryRepository.findById(id)
            .orElseThrow(() -> new InvalidCategory("유효하지 않은 카테고리입니다."));

        return new CategoryResponseDto(
            cateogory.getId(),
            cateogory.getName(),
            cateogory.getColor(),
            cateogory.getImageUrl(),
            cateogory.getDescription()
        );
    }

    public List<Long> getProductsInCategory(Long id) {
        List<Product> products = productRepository.findAllByCateogory_Id(id);

        return products.stream()
            .map(Product::getId)
            .collect(Collectors.toList());
    }

}
