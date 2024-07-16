package gift.repository;

import gift.domain.CategoryDTO;
import gift.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAll();
    Category findById(long id);
    void deleteById(long id);
    Category save(Category category);
}