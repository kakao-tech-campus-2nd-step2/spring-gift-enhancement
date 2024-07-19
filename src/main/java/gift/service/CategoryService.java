package gift.service;

import gift.domain.CategoryDTO;
import gift.entity.CategoryEntity;
import gift.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // 카테고리 전체 조회
    public List<CategoryDTO> getCategories() {
        List<CategoryEntity> categories = categoryRepository.findAll();
        return categories.stream().map(CategoryEntity::toDTO).collect(Collectors.toList());
    }

    // 카테고리 단일 조회
    public CategoryDTO getCategory(Long id) {
        CategoryEntity category = categoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("카테고리를 찾을 수 없습니다"));
        return category.toDTO(category);
    }

    // 카테고리 생성
    @Transactional
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        CategoryEntity category = new CategoryEntity(categoryDTO);
        CategoryEntity newCategory = categoryRepository.save(category);
        return CategoryEntity.toDTO(newCategory);
    }

    // 카테고리 수정
    @Transactional
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        CategoryEntity category = categoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("카테고리를 찾을 수 없습니다"));

        category.update(categoryDTO);
        return category.toDTO(category);
    }

    // 카테고리 삭제
    @Transactional
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

}