package gift.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import gift.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	Optional<Category> findByName(String nanme);
	
	boolean existsByName(String name);
}
