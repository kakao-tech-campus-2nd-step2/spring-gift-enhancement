package gift.repository;

import gift.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
}
