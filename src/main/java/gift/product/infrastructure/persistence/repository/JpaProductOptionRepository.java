package gift.product.infrastructure.persistence.repository;

import gift.product.infrastructure.persistence.entity.ProductOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaProductOptionRepository extends JpaRepository<ProductOptionEntity, Long> {

    List<ProductOptionEntity> findAllByProductId(Long productId);

    Optional<ProductOptionEntity> findByProductIdAndId(Long id, Long productId);

    Long countByProductId(Long productId);

    boolean existsByProductIdAndName(Long productId, String name);

}
