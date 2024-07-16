package gift.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepositoryInterface extends JpaRepository<String, Long> {
}
