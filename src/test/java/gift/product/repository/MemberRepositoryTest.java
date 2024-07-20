package gift.product.repository;

import gift.product.model.Member;
import gift.product.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void testSignUp() {
        Member member = new Member("member@email.com", "1234");
        Member registerMember = memberRepository.save(member);
        assertThat(registerMember.getId()).isNotNull();
        assertThat(registerMember.getEmail()).isEqualTo("member@email.com");
        assertThat(registerMember.getPassword()).isEqualTo("1234");
    }
}
