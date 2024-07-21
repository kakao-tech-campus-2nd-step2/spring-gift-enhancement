package gift.member.service;

import gift.member.model.Member;
import gift.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

    private String email = "test@example.com";
    private String password = "password";
    private Member member;

    @BeforeEach
    void setUp() {
        member = new Member(email, password);
        memberRepository.save(member);
    }

    @Test
    @DisplayName("잘못된 이메일로 로그인 시도할 경우")
    public void login_wrong_email() {
        // Given
        String wrongEmail = "wrong@example.com";

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> memberService.login(wrongEmail, password));
        assertThat(exception.getMessage()).isEqualTo("옳지 않은 이메일 입니다.");
    }

    @Test
    @DisplayName("잘못된 비밀번호로 로그인 시도할 경우")
    public void login_wrong_password() {
        // Given
        String wrongPassword = "wrongPassword";

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> memberService.login(email, wrongPassword));
        assertThat(exception.getMessage()).isEqualTo("옳지 않은 비밀번호 입니다.");
    }
}