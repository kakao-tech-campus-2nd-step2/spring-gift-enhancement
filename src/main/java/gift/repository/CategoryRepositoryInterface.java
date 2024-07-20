package gift.repository;

import gift.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepositoryInterface extends JpaRepository<Category, Long> {
    Category findCategoryByName(String categoryName);
}
