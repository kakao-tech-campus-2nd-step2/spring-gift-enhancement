package gift.repository;

import gift.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByName(String name);

    @Query("update Category c set c.name = :name where c.id = :id")
    void updateCategoryName(@Param("id") int id, @Param("name") String name);

    Page<Category> findAll(Pageable pageable);
}
