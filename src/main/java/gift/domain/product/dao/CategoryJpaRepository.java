package gift.domain.product.dao;

import gift.domain.product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryJpaRepository extends JpaRepository<Category, Long> {

}
