package gift.repository;

import gift.entity.OptionEntity;
import gift.entity.ProductEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<OptionEntity, Long> {

    List<OptionEntity> findAllByProductEntity(ProductEntity productEntity);

    Optional<OptionEntity> findByProductEntityAndId(ProductEntity productEntity, Long id);

    Optional<OptionEntity> findByProductEntityAndName( ProductEntity productEntity, String name);

}
