package gift.member.presentation;

import gift.auth.TokenService;
import gift.member.application.MemberResponse;
import gift.member.application.MemberService;
import gift.member.application.command.MemberEmailUpdateCommand;
import gift.member.application.command.MemberPasswordUpdateCommand;
import gift.member.domain.Member;
import gift.member.presentation.request.MemberJoinRequest;
import gift.member.presentation.request.MemberLoginRequest;
import gift.member.presentation.request.ResolvedMember;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @MockBean
    private TokenService tokenService;

    private Long memberId;
    private String email;
    private String password;
    private String token;

    @BeforeEach
    void setUp() {
        memberId = 1L;
        email = "test@example.com";
        password = "password";
        token = "testToken";

        when(tokenService.extractMemberId(eq(token))).thenReturn(memberId);
    }

    @Test
    void 회원가입_테스트() throws Exception {
        // Given
        MemberJoinRequest request = new MemberJoinRequest(email, password);
        when(memberService.join(request.toCommand())).thenReturn(memberId);
        when(tokenService.createToken(memberId)).thenReturn(token);

        // When & Then
        mockMvc.perform(post("/api/member/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"" + email + "\", \"password\":\"" + password + "\"}"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.AUTHORIZATION, "Bearer " + token));
    }

    @Test
    void 로그인_테스트() throws Exception {
        // Given
        MemberLoginRequest request = new MemberLoginRequest(email, password);
        when(memberService.login(request.toCommand())).thenReturn(memberId);
        when(tokenService.createToken(memberId)).thenReturn(token);

        // When & Then
        mockMvc.perform(post("/api/member/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"" + email + "\", \"password\":\"" + password + "\"}"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.AUTHORIZATION, "Bearer " + token));
    }

    @Test
    void 아이디로_찾기_테스트() throws Exception {
        // Given
        MemberResponse response = new MemberResponse(memberId, email, password);
        when(memberService.findById(eq(memberId))).thenReturn(response);

        // When & Then
        mockMvc.perform(get("/api/member/{id}", memberId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.password").value(password));
    }

    @Test
    void 전체_회원_찾기_테스트() throws Exception {
        // Given
        MemberResponse response1 = new MemberResponse(memberId, email, password);
        MemberResponse response2 = new MemberResponse(2L, "test2@example.com", "password2");
        when(memberService.findAll()).thenReturn(Arrays.asList(response1, response2));

        // When & Then
        mockMvc.perform(get("/api/member")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value(email))
                .andExpect(jsonPath("$[1].email").value("test2@example.com"));
    }

    @Test
    void 이메일_업데이트_테스트() throws Exception {
        // Given
        String newEmail = "test2@example.com";
//        Member member = new Member(memberId, email, password);
        ResolvedMember resolvedMember = new ResolvedMember(memberId);
        MemberEmailUpdateCommand expectedCommand = new MemberEmailUpdateCommand(newEmail);

        MemberResponse memberResponse = new MemberResponse(memberId, email, password);
        when(memberService.findById(anyLong())).thenReturn(memberResponse);

        // When & Then
        mockMvc.perform(put("/api/member/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"" + newEmail + "\"}")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk());

        verify(memberService).updateEmail(eq(expectedCommand), eq(resolvedMember));

    }

    @Test
    void 비밀번호_업데이트_테스트() throws Exception {
        // Given
        String newPassword = "newPassword";
//        Member member = new Member(memberId, email, password);
        ResolvedMember member = new ResolvedMember(memberId);
        MemberPasswordUpdateCommand updateCommand = new MemberPasswordUpdateCommand(newPassword);

        MemberResponse memberResponse = new MemberResponse(memberId, email, password);
        when(memberService.findById(anyLong())).thenReturn(memberResponse);

        // When & Then
        mockMvc.perform(put("/api/member/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"password\":\"" + newPassword + "\"}")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk());

        verify(memberService).updatePassword(eq(updateCommand), eq(member));
    }

    @Test
    void 회원_삭제_테스트() throws Exception {
        // Given
//        Member member = new Member(memberId, email, password);
        MemberResponse memberResponse = new MemberResponse(memberId, email, password);
        ResolvedMember member = new ResolvedMember(memberId);
        when(memberService.findById(anyLong())).thenReturn(memberResponse);

        // When & Then
        mockMvc.perform(delete("/api/member")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isNoContent());

        verify(memberService).delete(eq(member));
    }
}
