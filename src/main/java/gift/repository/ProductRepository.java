package gift.repository;

import gift.model.category.Category;
import gift.model.product.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAll(Pageable pageable);

    @Modifying
    @Query("update Product p set p.category.id = :defaultCategoryId where p.category.id = :categoryId")
    void updateCategory(Long categoryId, Long defaultCategoryId);

    @Query("select p from Product p join p.options o where o.id = :optionId")
    Product findByOptionId(Long optionId);

    @Query("select case when count(p) > 0 then true else false end from Product p join p.options o where o.id = :optionId")
    boolean existsByOptionId(@Param("optionId") Long optionId);
}
