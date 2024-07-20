package gift.administrator.category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Boolean existsByName(String name);

    Boolean existsByNameAndIdNot(String name, long id);
}
