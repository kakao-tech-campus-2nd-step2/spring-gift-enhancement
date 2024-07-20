package gift.application;

import gift.global.error.CustomException;
import gift.global.error.ErrorCode;
import gift.global.security.JwtUtil;
import gift.member.application.MemberService;
import gift.member.dao.MemberRepository;
import gift.member.dto.MemberDto;
import gift.member.entity.Member;
import gift.member.util.MemberMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import testFixtures.MemberFixture;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Test
    @DisplayName("회원가입 서비스 테스트")
    void registerMember() {
        MemberDto memberDto = new MemberDto("test@email.com", "password");
        Member member = MemberMapper.toEntity(memberDto);
        given(memberRepository.findByEmail(any()))
                .willReturn(Optional.empty());
        given(memberRepository.save(any()))
                .willReturn(member);

        memberService.registerMember(memberDto);

        verify(memberRepository).findByEmail(memberDto.email());
        verify(memberRepository).save(any());
    }

    @Test
    @DisplayName("회원가입 실패 테스트")
    void registerMemberFailed() {
        MemberDto memberDto = new MemberDto("test@email.com", "password");
        Member member = MemberMapper.toEntity(memberDto);
        given(memberRepository.findByEmail(any()))
                .willReturn(Optional.of(member));

        assertThatThrownBy(() -> memberService.registerMember(memberDto))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.MEMBER_ALREADY_EXISTS
                                     .getMessage());
    }

    @Test
    @DisplayName("회원 검증 서비스 테스트")
    void authenticate() {
        MemberDto memberDto = new MemberDto("test@email.com", "password");
        Member member = MemberMapper.toEntity(memberDto);
        String token = "token";
        given(jwtUtil.generateToken(any()))
                .willReturn(token);
        given(memberRepository.findByEmail(any()))
                .willReturn(Optional.of(member));

        String authToken = memberService.authenticate(memberDto);

        assertThat(authToken).isEqualTo(token);
    }

    @Test
    @DisplayName("존재하지 않는 회원 검증 실패 테스트")
    void authenticateMemberNotFound() {
        MemberDto memberDto = new MemberDto("test@email.com", "password");
        given(memberRepository.findByEmail(any()))
                .willReturn(Optional.empty());

        assertThatThrownBy(() -> memberService.authenticate(memberDto))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.MEMBER_NOT_FOUND
                                     .getMessage());
    }

    @Test
    @DisplayName("회원 비밀번호 검증 실패 테스트")
    void authenticateIncorrectPassword() {
        Member member = MemberFixture.createMember("test@email.com");
        MemberDto memberDto = new MemberDto("test@email.com", "incorrect " + member.getPassword());
        given(memberRepository.findByEmail(any()))
                .willReturn(Optional.of(member));

        assertThatThrownBy(() -> memberService.authenticate(memberDto))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.AUTHENTICATION_FAILED
                                     .getMessage());
    }

}