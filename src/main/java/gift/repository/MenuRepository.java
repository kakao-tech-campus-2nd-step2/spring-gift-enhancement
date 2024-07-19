package gift.repository;

import gift.domain.Menu;
import gift.domain.Option;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    Optional<Menu> findById(Long id);

    Page<Menu> findAll(Pageable pageable);

    Set<Option> getOptionsById(Long id);
}
