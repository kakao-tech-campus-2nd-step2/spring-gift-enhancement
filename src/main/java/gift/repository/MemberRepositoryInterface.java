package gift.repository;

import gift.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepositoryInterface extends JpaRepository<Member, Long> {
    Member findByEmail(String email);
}
