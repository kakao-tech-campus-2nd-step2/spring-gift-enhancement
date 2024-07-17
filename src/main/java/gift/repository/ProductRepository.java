package gift.repository;

import gift.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p JOIN FETCH p.category")
    Page<Product> findAllFetchJoin(Pageable pageable);


    @Query("SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.options WHERE p.id= :id")
    Optional<Product> findByIdFetchJoin(Long id);
}
