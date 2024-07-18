package gift.category.repository;

import gift.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryService extends JpaRepository<Category, Long> {

}
