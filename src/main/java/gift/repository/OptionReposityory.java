package gift.repository;

import gift.model.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OptionReposityory extends JpaRepository<Option, Long> {
    List<Option> findAllByProduct_Id(Long productId);
    void deleteByName(String name);
    Optional<Option> findByName(String name);
}
