package gift;

import gift.entity.Member;
import gift.repository.UserRepositoryInterface;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MemberRepositoryTest {
    private final UserRepositoryInterface users;

    public MemberRepositoryTest(UserRepositoryInterface userRepository) {
        users = userRepository;
    }

    @Test
    public void save(Member member) {
        users.save(member);
    }

}
