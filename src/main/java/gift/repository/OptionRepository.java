package gift.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import gift.entity.Option;

public interface OptionRepository extends JpaRepository<Option, Long>{

	List<Option> findByProductId(Long productId);
	Optional<Option> findByProductIdAndName(Long productId, String name);
}
