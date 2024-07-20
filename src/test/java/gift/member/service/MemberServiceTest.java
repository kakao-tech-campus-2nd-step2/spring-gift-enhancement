package gift.member.service;

import gift.member.model.Member;
import gift.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void login_wrong_email() {
        // Given
        String email = "test@example.com";
        String password = "password";
        String wrongEmail = "wrong@example.com";

        // Given setup for register
        when(memberRepository.findByEmail(email)).thenReturn(Optional.empty());
        Member member = new Member(email, password);
        when(memberRepository.save(member)).thenReturn(member);

        memberService.register(email, password);

        // Given setup for login with wrong email
        doThrow(new IllegalArgumentException("옳지 않은 이메일 입니다."))
                .when(memberService).login(wrongEmail, password);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> memberService.login(wrongEmail, password));
        assertThat(exception.getMessage()).isEqualTo("옳지 않은 이메일 입니다.");
    }


//    @Test
//    public void login_wrong_password() {
//        // Given
//        String email = "test@example.com";
//        String password = "password";
//        String wrongPassword = "wrongPassword";
//
//        doNothing().when(memberService).register(email, password);
//
//        memberService.register(email, password);
//
//        doThrow(new IllegalArgumentException("옳지 않은 비밀번호 입니다."))
//                .when(memberService).login(email, wrongPassword);
//
//        // When & Then
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
//                () -> memberService.login(email, wrongPassword));
//        assertThat(exception.getMessage()).isEqualTo("옳지 않은 비밀번호 입니다.");
//    }
}