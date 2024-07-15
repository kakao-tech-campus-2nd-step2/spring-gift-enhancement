package gift.service;

import gift.controller.dto.CategoryRequestDTO;
import gift.controller.dto.CategoryResponseDTO;
import gift.domain.Category;
import gift.repository.CategoryRepository;
import gift.utils.error.CategoryNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @Transactional
    public List<CategoryResponseDTO> findAllCategory(){
        List<CategoryResponseDTO> categories = categoryRepository.findAll().stream().map(
            category -> new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getColor(),
                category.getImageUrl(),
                category.getDescription()
            )
        ).toList();
        return categories;
    }
    @Transactional
    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO){
        Category category = new Category(categoryRequestDTO.getName(),categoryRequestDTO.getColor(),
            categoryRequestDTO.getImageUrl(),categoryRequestDTO.getDescription());
        Category save = categoryRepository.save(category);
        return new CategoryResponseDTO(save.getId(),save.getName(), save.getColor(), save.getImageUrl(),
            save.getDescription());
    }
    @Transactional
    public void updateCategory(Long id, CategoryRequestDTO categoryRequestDTO){
        Category category = categoryRepository.findById(id).orElseThrow(
            () -> new CategoryNotFoundException("Category Not Found")
        );
        category.updateCategory(categoryRequestDTO);

    }
    @Transactional
    public void deleteCategory(Long id){
        Category category = categoryRepository.findById(id).orElseThrow(
            () -> new CategoryNotFoundException("Category Not Found")
        );
        categoryRepository.deleteById(id);
    }
}
