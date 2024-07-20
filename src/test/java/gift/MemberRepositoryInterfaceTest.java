package gift;

import gift.entity.Member;
import gift.repository.MemberRepositoryInterface;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MemberRepositoryInterfaceTest {
    private final MemberRepositoryInterface users;

    public MemberRepositoryInterfaceTest(MemberRepositoryInterface memberRepositoryInterface) {
        users = memberRepositoryInterface;
    }

    @Test
    public void save(Member member) {
        users.save(member);
    }

}
