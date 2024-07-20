package gift;

import gift.entity.Member;
import gift.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MemberRepositoryTest {
    private final MemberRepository users;

    public MemberRepositoryTest(MemberRepository memberRepository) {
        users = memberRepository;
    }

    @Test
    public void save(Member member) {
        users.save(member);
    }

}
