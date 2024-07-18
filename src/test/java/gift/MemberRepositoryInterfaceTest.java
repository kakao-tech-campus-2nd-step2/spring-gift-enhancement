package gift;

import gift.entity.Member;
import gift.repository.UserRepositoryInterface;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MemberRepositoryInterfaceTest {
    private final UserRepositoryInterface users;

    public MemberRepositoryInterfaceTest(UserRepositoryInterface userRepositoryInterface) {
        users = userRepositoryInterface;
    }

    @Test
    public void save(Member member) {
        users.save(member);
    }

}
