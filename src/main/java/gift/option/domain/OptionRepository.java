package gift.option.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionRepository extends JpaRepository<Option, Long> {

    List<Option> findAllByProductId(Long productId);
}
