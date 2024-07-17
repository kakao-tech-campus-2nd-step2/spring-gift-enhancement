package gift.service;

import gift.database.JpaCategoryRepository;
import gift.dto.CategoryDTO;
import gift.dto.CategoryResponse;
import gift.model.Category;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService{

    private JpaCategoryRepository jpaCategoryRepository;

    public CategoryServiceImpl(JpaCategoryRepository jpaCategoryRepository) {
        this.jpaCategoryRepository = jpaCategoryRepository;
    }

    @Override
    public List<CategoryResponse> readAll() {
        return jpaCategoryRepository.findAll()
            .stream()
            .map(CategoryResponse::new)
            .toList();

    }

    @Override
    public void create(CategoryDTO categoryDTO) {
        Category category = getCategory(null,categoryDTO);

        jpaCategoryRepository.save(category);
    }


    @Override
    public void update(long id, CategoryDTO categoryDTO) {
        Category category = getCategory(id,categoryDTO);
        jpaCategoryRepository.save(category);
    }

    @Override
    public void delete(long id) {
        jpaCategoryRepository.deleteById(id);
    }

    private static Category getCategory(Long id,CategoryDTO categoryDTO) {
        return new Category(id,
            categoryDTO.getName(),
            categoryDTO.getColor(),
            categoryDTO.getDescription(),
            categoryDTO.getImageUrl());
    }

}
