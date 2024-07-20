package gift.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<String, Long> {
}
