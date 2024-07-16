package gift.domain.product.service;

import gift.domain.product.dao.CategoryJpaRepository;
import gift.domain.product.entity.Category;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryJpaRepository categoryJpaRepository;

    public CategoryService(CategoryJpaRepository categoryJpaRepository) {
        this.categoryJpaRepository = categoryJpaRepository;
    }

    public List<Category> readAll() {
        return categoryJpaRepository.findAll();
    }
}
