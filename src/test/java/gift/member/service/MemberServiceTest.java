//import gift.Application;
//import gift.member.model.Member;
//import gift.member.repository.MemberRepository;
//import gift.member.service.MemberService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.transaction.annotation.Transactional;
//
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//@SpringBootTest(classes = Application.class)
//@Transactional
//public class MemberServiceTest {
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private MemberService memberService;
//
//    @BeforeEach
//    public void setUp() {
//        memberRepository.deleteAll();
//    }
//
//    @Test
//    public void 등록이_안되는_경우() {
//        // Given
//        String email = "new@example.com";
//        String password = "newPassword";
//
//        // When
//        Member registeredMember = memberService.register(email, password);
//
//        // Then
//        assertThat(registeredMember.getEmail()).isEqualTo(email);
//        assertThat(passwordEncoder.matches(password, registeredMember.getPassword())).isTrue();
//    }
//
//    @Test
//    public void 이메일이_이미_존재하는_경우() {
//        // Given
//        String email = "test@example.com";
//        String password = "password";
//        memberService.register(email, password);
//
//        // When & Then
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> memberService.register(email, password));
//        assertThat(exception.getMessage()).isEqualTo("이미 존재하는 이메일 입니다.");
//    }
//
//    @Test
//    public void 옳지_않은_경우() {
//        // Given
//        String email = "test@example.com";
//        String password = "password";
//        memberService.register(email, password);
//
//        // When & Then
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> memberService.login(email, "wrongpassword"));
//        assertThat(exception.getMessage()).isEqualTo("옳지 않은 이메일이나 비밀번호 입니다.");
//    }
//
//    @Test
//    public void 로그인이_실패한_경우() {
//        // Given
//        String email = "test@example.com";
//        String password = "password";
//        memberService.register(email, password);
//
//        // When
//        Member loggedInMember = memberService.login(email, password);
//
//        // Then
//        assertThat(loggedInMember.getEmail()).isEqualTo(email);
//        assertThat(passwordEncoder.matches(password, loggedInMember.getPassword())).isTrue();
//    }
//
//    @Test
//    public void 이메일_수정이_실패한_경우() {
//        // Given
//        Member member = memberService.register("oldemail@example.com", "password");
//        Long memberId = member.getMemberId();
//        String newEmail = "newemail@example.com";
//
//        // When
//        Member result = memberService.updateEmail(memberId, newEmail);
//
//        // Then
//        assertThat(result.getEmail()).isEqualTo(newEmail);
//    }
//
//    @Test
//    public void 비밀번호_수정이_실패한_경우() {
//        // Given
//        Member member = memberService.register("test@example.com", "oldPassword");
//        Long memberId = member.getMemberId();
//        String newPassword = "newPassword";
//
//        // When
//        Member result = memberService.updatePassword(memberId, newPassword);
//
//        // Then
//        assertThat(passwordEncoder.matches(newPassword, result.getPassword())).isTrue();
//    }
//
//    @Test
//    public void 삭제가_실패한_경우() {
//        // Given
//        Member member = memberService.register("test@example.com", "password");
//        Long memberId = member.getMemberId();
//
//        // When
//        memberService.deleteMember(memberId);
//
//        // Then
//        assertThat(memberRepository.findById(memberId)).isEmpty();
//    }
//}