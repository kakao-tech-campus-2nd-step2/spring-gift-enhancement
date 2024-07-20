package gift.repository;

import gift.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {


    Category findCategoryByName(String categoryName);

    Category getCategoryByProductId(Long productId);
}
