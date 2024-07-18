import gift.Application;
import gift.member.repository.MemberRepository;
import gift.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** @Mock 을 사용하면 테스트 성공이 뜨지만 사용하지 않을 경우,
 * java.lang.IllegalStateException 오류 -> 해당 파일 경로 명시 */
@SpringBootTest(classes = Application.class)
@Transactional
public class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberService memberService;

    @BeforeEach
    public void setUp() {
        memberRepository.deleteAll();
    }

    @Test
    public void 이메일이_이미_존재하는_경우() {
        // Given: 이미 존재하는 이메일로 회원 등록
        String email = "test@example.com";
        String password = "password";
        memberService.register(email, password);

        // When & Then: 동일한 이메일로 다시 등록 시도 시 예외 발생 확인
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> memberService.register(email, password));
        assertThat(exception.getMessage()).isEqualTo("이미 존재하는 이메일 입니다.");
    }

    @Test
    public void 잘못된_비밀번호로_로그인을_시도하는_경우() {
        // Given: 회원 등록
        String email = "test@example.com";
        String password = "password";
        memberService.register(email, password);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> memberService.login(email, "wrong"));
        assertThat(exception.getMessage()).isEqualTo("옳지 않은 이메일이나 비밀번호 입니다.");
    }
}
