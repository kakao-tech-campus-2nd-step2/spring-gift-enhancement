package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import gift.product.dto.JwtResponse;
import gift.product.dto.MemberDto;
import gift.product.exception.LoginFailedException;
import gift.product.model.Member;
import gift.product.repository.AuthRepository;
import gift.product.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class AuthServiceTest {

    final String EMAIL = "test@test.com";
    final String PASSWORD = "test";
    @Mock
    AuthRepository authRepository;
    @InjectMocks
    AuthService authService;

    @BeforeEach
    void 시크릿_키_셋팅() {
        ReflectionTestUtils.setField(authService, "SECRET_KEY",
            "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=");
    }

    @Test
    void 회원가입() {
        //given
        MemberDto memberDto = new MemberDto(EMAIL, PASSWORD);
        given(authRepository.existsByEmail(EMAIL)).willReturn(false);

        //when
        authService.register(memberDto);

        //then
        then(authRepository).should().save(any());
    }

    @Test
    void 로그인() {
        //given
        MemberDto memberDto = new MemberDto(EMAIL, PASSWORD);
        given(authRepository.findByEmail(EMAIL)).willReturn(new Member(1L, EMAIL, PASSWORD));
        given(authRepository.existsByEmail(EMAIL)).willReturn(true);

        //when
        JwtResponse jwtResponse = authService.login(memberDto);

        //then
        assertThat(jwtResponse.token()).isNotEmpty();
    }

    @Test
    void 회원가입_중복() {
        //given
        MemberDto memberDto = new MemberDto(EMAIL, PASSWORD);
        given(authRepository.existsByEmail(EMAIL)).willReturn(false);
        authService.register(memberDto);
        given(authRepository.existsByEmail(EMAIL)).willReturn(true);

        //when, then
        assertThatThrownBy(() -> authService.register(memberDto)).isInstanceOf(
            IllegalArgumentException.class);
    }

    @Test
    void 존재하지_않는_회원_로그인() {
        //given
        MemberDto memberDto = new MemberDto(EMAIL, PASSWORD);
        given(authRepository.existsByEmail(EMAIL)).willReturn(false);

        //when, then
        assertThatThrownBy(() -> authService.login(memberDto)).isInstanceOf(
            LoginFailedException.class);
    }
}
