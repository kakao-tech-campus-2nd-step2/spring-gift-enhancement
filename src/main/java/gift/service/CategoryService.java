package gift.service;

import gift.dto.CategoryRequestDto;
import gift.dto.CategoryResponseDto;
import gift.entity.Category;
import gift.repository.CategoryRepository;
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

    public List<CategoryResponseDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
            .map(CategoryResponseDto::new)
            .collect(Collectors.toList());
    }

    public CategoryResponseDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("아이디 " + id + "에 해당 하는 상품을 찾을 수 없습니다."));

        return new CategoryResponseDto(category);
    }

    public void addCategory(CategoryRequestDto request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("이미 존재하는 상품입니다.");
        }
        Category category = new Category(request.getName(), request.getImageUrl(), request.getColor(), request.getDescription());
        categoryRepository.save(category);
    }

    public void updateCategory(Long id, CategoryRequestDto request) {
        if (!categoryRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("해당 이름의 상품이 존재하지 않습니다.");
        }
        Category category = new Category(id, request.getName(), request.getImageUrl(), request.getColor(), request.getDescription());
        categoryRepository.save(category);
    }

    public void deleteCategoryById(Long id) {
        categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("삭제할 아이템을 발견할 수 없습니다."));
        categoryRepository.deleteById(id);
    }
}
