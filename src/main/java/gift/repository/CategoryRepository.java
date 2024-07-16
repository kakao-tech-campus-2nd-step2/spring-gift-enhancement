package gift.repository;

import gift.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAll();
    Category findById(int id);
    void deleteById(int id);
    Category save(Category category);
}