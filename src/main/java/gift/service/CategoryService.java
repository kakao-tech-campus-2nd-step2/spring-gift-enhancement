package gift.service;

import gift.domain.Category;
import gift.dto.requestDTO.CategoryRequestDTO;
import gift.repository.JpaCategoryRepository;
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
}
