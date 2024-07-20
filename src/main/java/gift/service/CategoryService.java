package gift.service;

import gift.dto.CategoryRequestDTO;
import gift.dto.CategoryResponseDTO;
import gift.dto.WishResponseDTO;
import gift.entity.Product;
import gift.entity.Category;
import gift.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponseDTO> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public void addCategory(CategoryRequestDTO categoryRequestDTO) {
        System.out.println("add categoty");
        Category category = toEntity(categoryRequestDTO);
        categoryRepository.save(category);
    }


    private CategoryResponseDTO toDTO(Category category) {
        List<String> productNames = category.getProducts().stream()
                .map(Product::getName)
                .collect(Collectors.toList());

        return new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getColor(),
                category.getDescription(),
                category.getImageUrl(),
                productNames
        );
    }

    private Category toEntity(CategoryRequestDTO categoryRequestDTO) {
        Category category = new Category(
                categoryRequestDTO.name(),
                categoryRequestDTO.color(),
                categoryRequestDTO.description(),
                categoryRequestDTO.imageUrl()
        );
        return category;
    }

}
