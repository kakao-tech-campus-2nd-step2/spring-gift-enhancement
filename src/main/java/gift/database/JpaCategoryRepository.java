package gift.database;

import gift.model.Category;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCategoryRepository extends JpaRepository<Category, Long> {

    @Override
    <S extends Category> S save(S entity);

    @Override
    Optional<Category> findById(Long id);

    @Override
    void delete(Category entity);

    @Override
    List<Category> findAll();

}
