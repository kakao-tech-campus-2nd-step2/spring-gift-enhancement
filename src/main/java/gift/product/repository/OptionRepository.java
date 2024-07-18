package gift.product.repository;

import gift.product.model.Option;
import gift.product.model.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface OptionRepository extends JpaRepository<Option, Integer> {
    Optional<Option> findById(Long id);
    void deleteAllByProductId(Long productId);
    @Transactional
    void deleteById(Long id);
    Page<Option> findAllByProductId(Long productId, Pageable pageable);
    List<Option> findAllByProduct(Product product);
    @Query("select count(*) from Option o "
        + "where o.product = (select op.product from Option op where op.id = ?1)")
    int countAllByProduct(Long id);
    boolean existsById(Long id);
    boolean existsByProductId(Long productId);
}
