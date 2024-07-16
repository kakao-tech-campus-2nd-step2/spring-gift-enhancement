package gift.service;

import gift.domain.Category;
import gift.dto.requestDTO.CategoryRequestDTO;
import gift.dto.responseDTO.CategoryListResponseDTO;
import gift.dto.responseDTO.CategoryResponseDTO;
import gift.repository.JpaCategoryRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryService {
    private final JpaCategoryRepository jpaCategoryRepository;

    public CategoryService(JpaCategoryRepository jpaCategoryRepository) {
        this.jpaCategoryRepository = jpaCategoryRepository;
    }

    public Long addCategory(CategoryRequestDTO categoryRequestDTO){
        Category category = CategoryRequestDTO.toEntity(categoryRequestDTO);
        return jpaCategoryRepository.save(category).getId();
    }

    @Transactional(readOnly = true)
    public CategoryResponseDTO getOneCategory(Long categoryId){
        Category category = jpaCategoryRepository.findById(categoryId)
            .orElseThrow(() -> new NoSuchElementException("id가 잘못되었습니다."));
        return CategoryResponseDTO.of(category);
    }

    @Transactional(readOnly = true)
    public CategoryListResponseDTO getAllCategories(){
        List<CategoryResponseDTO> categoryResponseDTOList = jpaCategoryRepository.findAll()
            .stream()
            .map(CategoryResponseDTO::of)
            .toList();

        return new CategoryListResponseDTO(categoryResponseDTOList);
    }

    public Long updateCategory(Long categoryId, CategoryRequestDTO categoryRequestDTO) {
        Category category = jpaCategoryRepository.findById(categoryId)
            .orElseThrow(() -> new NoSuchElementException("id가 잘못되었습니다."));

        category.update(categoryRequestDTO.name(), categoryRequestDTO.color(),
            categoryRequestDTO.imageUrl(),
            categoryRequestDTO.description());

        return category.getId();
    }

    public Long deleteCategory(Long categoryId) {
        Category category = jpaCategoryRepository.findById(categoryId)
            .orElseThrow(() -> new NoSuchElementException("id가 잘못되었습니다."));
        jpaCategoryRepository.delete(category);
        return category.getId();
    }
}
