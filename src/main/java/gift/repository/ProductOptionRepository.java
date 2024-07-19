package gift.repository;

import gift.entity.ProductOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ProductOption 엔티티를 위한 JPA 레포지토리 인터페이스.
 */
@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOptionEntity, Long> {
    ProductOptionEntity getProductOptionById(Long id);

}
