package gift.service;

import gift.dto.CategoryRequestDTO;
import gift.dto.WishResponseDTO;
import gift.entity.Category;
import gift.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public void addCategory(CategoryRequestDTO categoryRequestDTO) {
        Category category = toEntity(categoryRequestDTO);
        categoryRepository.save(category);
    }


    public Category toEntity(CategoryRequestDTO categoryRequestDTO) {
        Category category = new Category(
                categoryRequestDTO.name(),
                categoryRequestDTO.color(),
                categoryRequestDTO.description(),
                categoryRequestDTO.imageUrl()
        );
        return category;
    }

}
