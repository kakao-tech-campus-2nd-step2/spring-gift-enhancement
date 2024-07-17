package gift.repository;

import gift.model.category.Category;
import gift.model.product.Product;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAll(Pageable pageable);

    @Modifying
    @Query("update Product p set p.category.id = :defaultCategoryId where p.category.id = :categoryId")
    void updateCategory(Long categoryId, Long defaultCategoryId);
}
