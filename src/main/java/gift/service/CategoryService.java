package gift.service;

import gift.dto.CategoryRequestDTO;
import gift.dto.CategoryResponseDTO;
import gift.entity.Product;
import gift.entity.Category;
import gift.exception.categortException.CategoryNotFoundException;
import gift.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Category findById(Long categoryId){
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        return category;
    }

    public List<CategoryResponseDTO> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public void addCategory(CategoryRequestDTO categoryRequestDTO) {
        Category category = toEntity(categoryRequestDTO);
        categoryRepository.save(category);
    }


    @Transactional
    public void removeCategory(Long categoryId){
        Category category = findById(categoryId);
        categoryRepository.deleteById(categoryId);
    }

    @Transactional
    public void updateCategory(Long categoryId, CategoryRequestDTO categoryRequestDTO){
        Category category = findById(categoryId);
        category.updateCategory(categoryRequestDTO);
        categoryRepository.save(category);
    }


    private CategoryResponseDTO toDTO(Category category) {
        List<String> productNames = category.getProducts()
                .stream()
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
