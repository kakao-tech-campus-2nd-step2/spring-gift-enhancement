package gift.repository;

import gift.dto.product.ShowProductDTO;
import gift.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("select new gift.dto.product.ShowProductDTO(p.id, p.name, p.price, p.imageUrl, p.category.name) from Product p ")
    Page<ShowProductDTO> findAllProduct(Pageable pageable);
}
