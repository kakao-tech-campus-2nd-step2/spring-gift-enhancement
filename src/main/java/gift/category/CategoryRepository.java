package gift.category;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Cateogory,Long> {

    Optional<Cateogory> findByName(String name);

}
