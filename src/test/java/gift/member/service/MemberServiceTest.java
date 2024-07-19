package gift.member.service;

import gift.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @BeforeEach
    public void setUp() {
        memberRepository.deleteAll();
    }

    @Test
    public void 잘못된_이메일로_로그인을_시도하는_경우() {
        // Given
        String email = "test@example.com";
        String password = "password";

        memberService.register(email, password);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> memberService.login("wrong@example.com", password));
        assertThat(exception.getMessage()).isEqualTo("옳지 않은 이메일 입니다.");
    }

    @Test
    public void 잘못된_비밀번호로_로그인을_시도하는_경우() {
        // Given
        String email = "test@example.com";
        String password = "password";
        String wrongPassword = "wrongPassword";

        memberService.register(email, password);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> memberService.login(email, wrongPassword));
        assertThat(exception.getMessage()).isEqualTo("옳지 않은 비밀번호 입니다.");
    }

}