package gift.repository;

import gift.domain.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Products, Long> {
    Optional<Products> findByName(String name);
    Page<Products> findAll(Pageable pageable);
}
