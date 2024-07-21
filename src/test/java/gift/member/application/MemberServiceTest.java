package gift.member.application;

import gift.exception.type.NotFoundException;
import gift.member.application.command.MemberEmailUpdateCommand;
import gift.member.application.command.MemberJoinCommand;
import gift.member.application.command.MemberLoginCommand;
import gift.member.application.command.MemberPasswordUpdateCommand;
import gift.member.domain.Member;
import gift.member.domain.MemberRepository;
import gift.member.presentation.request.ResolvedMember;
import gift.wishlist.domain.WishlistRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private WishlistRepository wishlistRepository;

    @InjectMocks
    private MemberService memberService;

    private Member member;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        member = new Member(1L, "test@example.com", "password");
    }

    @Test
    void 회원가입_테스트() {
        // Given
        MemberJoinCommand command = new MemberJoinCommand("test@example.com", "password");
        when(memberRepository.save(any())).thenReturn(member);

        // When
        Long memberId = memberService.join(command);

        // Then
        assertEquals(member.getId(), memberId);
        verify(memberRepository, times(1)).save(any());
    }

    @Test
    void 로그인_테스트() {
        // Given
        MemberLoginCommand command = new MemberLoginCommand("test@example.com", "password");
        when(memberRepository.findByEmailAndPassword(any(), any())).thenReturn(Optional.of(member));

        // When
        Long memberId = memberService.login(command);

        // Then
        assertEquals(member.getId(), memberId);
        verify(memberRepository, times(1)).findByEmailAndPassword(any(), any());
    }

    @Test
    void 이메일_업데이트_테스트() {
        // Given
        MemberEmailUpdateCommand command = new MemberEmailUpdateCommand("new@example.com");
        ResolvedMember resolvedMember = new ResolvedMember(member.getId());
        when(memberRepository.findById(resolvedMember.id())).thenReturn(Optional.of(member));
        when(memberRepository.existsByEmail(command.email())).thenReturn(false);

        // When
        assertDoesNotThrow(() -> memberService.updateEmail(command, resolvedMember));

        // Then
        Assertions.assertThat(member.getEmail()).isEqualTo("new@example.com");
    }

    @Test
    void 비밀번호_업데이트_테스트() {
        // Given
        MemberPasswordUpdateCommand command = new MemberPasswordUpdateCommand("newPassword");
        ResolvedMember resolvedMember = new ResolvedMember(1L);
        when(memberRepository.findById(resolvedMember.id())).thenReturn(Optional.of(this.member));

        // When
        assertDoesNotThrow(() -> memberService.updatePassword(command, resolvedMember));

        // Then

        Assertions.assertThat(memberService.findById(resolvedMember.id()).password()).isEqualTo("newPassword");
    }

    @Test
    void ID로_회원_찾기_테스트() {
        // Given
        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));

        // When
        MemberResponse response = memberService.findById(member.getId());

        // Then
        assertEquals(member.getId(), response.id());
        verify(memberRepository, times(1)).findById(member.getId());
    }

    @Test
    void ID로_회원_찾기_테스트_회원_없음() {
        // Given
        when(memberRepository.findById(member.getId())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundException.class, () -> memberService.findById(member.getId()));
    }

    @Test
    void 전체_회원_찾기_테스트() {
        // Given
        Member member2 = new Member("test2@example.com", "password2");
        when(memberRepository.findAll()).thenReturn(Arrays.asList(member, member2));

        // When
        List<MemberResponse> responses = memberService.findAll();

        // Then
        assertEquals(2, responses.size());
        assertEquals(member.getEmail(), responses.get(0).email());
        assertEquals(member2.getEmail(), responses.get(1).email());
        verify(memberRepository, times(1)).findAll();
    }

    @Test
    void 회원_삭제_테스트() {
        // Given
        doNothing().when(memberRepository).delete(member);
        doNothing().when(wishlistRepository).deleteAllByMemberId(member.getId());
        ResolvedMember resolvedMember = new ResolvedMember(member.getId());
        when(memberRepository.findById(resolvedMember.id())).thenReturn(Optional.of(member));
        // When
        assertDoesNotThrow(() -> memberService.delete(resolvedMember));

        // Then
        verify(memberRepository, times(1)).delete(member);
        verify(wishlistRepository, times(1)).deleteAllByMemberId(member.getId());
    }
}
