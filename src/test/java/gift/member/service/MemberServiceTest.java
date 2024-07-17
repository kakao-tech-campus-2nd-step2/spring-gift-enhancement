package gift.member.service;

import gift.member.model.Member;
import gift.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberService memberService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createSuccess() {
        // Given
        String email = "test@example.com";
        String password = "password";
        String encodedPassword = "encodedPassword";
        Member member = new Member(email, encodedPassword);

        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        // When
        Member createdMember = memberService.createMember(email, password);

        // Then
        assertThat(createdMember.getEmail()).isEqualTo(email);
        assertThat(createdMember.getPassword()).isEqualTo(encodedPassword);
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    public void registerSuccess() {
        // Given
        String email = "new@example.com";
        String password = "newPassword";
        String encodedPassword = "encodedPassword";
        Member member = new Member(email, encodedPassword);

        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(memberRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        // When
        Member registeredMember = memberService.register(email, password);

        // Then
        assertThat(registeredMember.getEmail()).isEqualTo(email);
        assertThat(registeredMember.getPassword()).isEqualTo(encodedPassword);
        verify(memberRepository, times(1)).findByEmail(email);
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    public void throwExceptionWhenEmailAlreadyExists() {
        // Given
        String email = "test@example.com";
        String password = "password";

        when(memberRepository.findByEmail(email)).thenReturn(Optional.of(new Member(email, "encodedPassword")));

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> memberService.register(email, password));
        assertThat(exception.getMessage()).isEqualTo("이미 존재하는 이메일 입니다.");
    }

    @Test
    public void throwExceptionWhenInvalidCredentials() {
        // Given
        String email = "test@example.com";
        String password = "password";
        String encodedPassword = "encodedPassword";
        Member member = new Member(email, encodedPassword);

        when(memberRepository.findByEmail(email)).thenReturn(Optional.of(member));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(false);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> memberService.login(email, "wrongpassword"));
        assertThat(exception.getMessage()).isEqualTo("옳지 않은 이메일이나 비밀번호 입니다.");
    }


    @Test
    public void loginSuccess() {
        // Given
        String email = "test@example.com";
        String password = "password";
        String encodedPassword = "encodedPassword";
        Member member = new Member(email, encodedPassword);

        when(memberRepository.findByEmail(email)).thenReturn(Optional.of(member));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);

        // When
        Member loggedInMember = memberService.login(email, password);

        // Then
        assertThat(loggedInMember.getEmail()).isEqualTo(email);
        assertThat(loggedInMember.getPassword()).isEqualTo(encodedPassword);
    }

    @Test
    public void updateEmailSuccess() {
        // Given
        Long memberId = 1L;
        String newEmail = "newemail@example.com";
        Member member = new Member("oldemail@example.com", "password");

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        Member updatedMember = new Member(newEmail, member.getPassword());
        when(memberRepository.save(any(Member.class))).thenReturn(updatedMember);

        // When
        Member result = memberService.updateEmail(memberId, newEmail);

        // Then
        assertThat(result.getEmail()).isEqualTo(newEmail);
        verify(memberRepository, times(1)).findById(memberId);
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    public void updatePasswordSuccess() {
        // Given
        Long memberId = 1L;
        String newPassword = "newPassword";
        String encodedNewPassword = "encodedNewPassword";
        Member member = new Member("test@example.com", "oldPassword");

        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(passwordEncoder.encode(newPassword)).thenReturn(encodedNewPassword);
        Member updatedMember = new Member(member.getEmail(), encodedNewPassword);
        when(memberRepository.save(any(Member.class))).thenReturn(updatedMember);

        // When
        Member result = memberService.updatePassword(memberId, newPassword);

        // Then
        assertThat(result.getPassword()).isEqualTo(encodedNewPassword);
        verify(memberRepository, times(1)).findById(memberId);
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    public void deleteSuccess() {
        // Given
        Long memberId = 1L;

        // When
        memberService.deleteMember(memberId);

        // Then
        verify(memberRepository, times(1)).deleteById(memberId);
    }
}
