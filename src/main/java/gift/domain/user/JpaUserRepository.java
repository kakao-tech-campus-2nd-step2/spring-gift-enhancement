package gift.domain.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends JpaRepository<User, Long> {


    @Modifying
    @Query(value = "DELETE FROM cart_item WHERE user_id = :userId; " +
                   "DELETE FROM users WHERE id = :userId", nativeQuery = true)
    void deleteById(Long userId);

    boolean existsByEmail(String email);

    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findById(Long userId);

}
