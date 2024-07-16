package gift.repository.category;

import gift.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorySpringDataJpaRepository extends JpaRepository<Category, Long>  {
}
