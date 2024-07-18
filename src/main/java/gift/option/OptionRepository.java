package gift.option;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Long> {

    Options findAllByProductId(Long productId);

    void deleteById(Long id);
}
