package gift.repository;

import gift.dto.category.ShowCategoryDTO;
import gift.entity.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByName(String name);

    @Modifying
    @Transactional
    @Query("update Category c set c.name = :name where c.id = :id")
    void updateCategoryName(@Param("id") int id, @Param("name") String name);

    @Query("select new gift.dto.category.ShowCategoryDTO(c.id,c.name) from Category c")
    Page<ShowCategoryDTO> findAllCategory(Pageable pageable);

}
