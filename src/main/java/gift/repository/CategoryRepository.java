package gift.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gift.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	
}
