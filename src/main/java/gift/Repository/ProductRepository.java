package gift.Repository;

import gift.Model.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findProductById(Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Product product SET product.category = null WHERE product.category.id = :id")
    void changeCategoryNull(@Param("id") Long id);

}
