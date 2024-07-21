package gift.controller.member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import gift.domain.Grade;
import gift.service.MemberService;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @MockBean
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("사용자 목록 조회 테스트")
    @Test
    void getAllMembersTest() {

    }

    @DisplayName("사용자 조회 테스트")
    @Test
    void getMember() {
    }

    @DisplayName("회원가입 테스트0 : 성공")
    @Test
    void signUpTest0() throws Exception {
        // given
        SignUpRequest request = new SignUpRequest("validEmail@kakao.com", "validPassword",
            "validNickName");
        MemberResponse response = new MemberResponse(UUID.randomUUID(), "ValidEmail@kakao.com",
            "validPassword", "validNickName", Grade.USER);
        doReturn(response).when(memberService).save(any(SignUpRequest.class));

        // when
        ResultActions resultActions = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/members/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        );

        // then
        resultActions.andExpect(status().isCreated())
            .andExpect(jsonPath("email", response.email()).exists())
            .andExpect(jsonPath("password", response.password()).exists())
            .andExpect(jsonPath("nickName", response.nickName()).exists())
            .andReturn();
    }

    @DisplayName("회원가입 테스트1 : 실패")
    @Test
    void signUpTest1() throws Exception {
        // given
        SignUpRequest[] requests = {
            new SignUpRequest(null, "validPassword", "validNickName"),
            new SignUpRequest("tooMuchLongEmail@kakao.com", "validPassword", "validNickName"),
            new SignUpRequest("invalidEmailForm", "validPassword", "validNickName"),
            new SignUpRequest("validEmail@kakao.com", "shortPw", "validNickName"),
            new SignUpRequest("validEmail@kakao.com", "validPassword", null),
            new SignUpRequest("validEmail@kakao.com", "validPassword", "tooMuchLongNickName")
        };
        String[] expected = {
            "이메일은 필수 입력 항목입니다.",
            "이메일은 최대 30자 이내입니다.",
            "적절한 이메일 형식이 아닙니다",
            "비밀번호의 길이는 8자 이상, 20자 이하 이내입니다.",
            "닉네임은 필수 입력 항목입니다.",
            "닉네임은 최대 15자까지 가능합니다."
        };

        // when
        for (int i = 0; i < requests.length; i++) {
            int finalIdx = i;
            mockMvc.perform(
                    MockMvcRequestBuilders.post("/api/members/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requests[i]))
                )

                // then
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertInstanceOf(MethodArgumentNotValidException.class,
                    result.getResolvedException()))
                .andExpect(result -> {
                    assertThat(((BindException) result.getResolvedException()).getBindingResult()
                        .getFieldError()
                        .getDefaultMessage()).isEqualTo(expected[finalIdx]);
                })
                .andReturn();
        }
    }

    @DisplayName("회원가입 테스트2 : 검증 실패")
    @Test
    void signUpTest2() throws Exception {
        // given
        SignUpRequest request = new SignUpRequest("id00@email.com", "shortpw",
            "testName00");
        MemberResponse response = new MemberResponse(UUID.randomUUID(), "id00@email.com",
            "shortpw", "testName00", Grade.USER);
        doReturn(response).when(memberService).save(any(SignUpRequest.class));

        // when
        ResultActions resultActions = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/members/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        );

        // then
        resultActions.andExpect(status().isBadRequest())
            .andExpect(result -> assertTrue(
                result.getResolvedException() instanceof MethodArgumentNotValidException))
            .andExpect(result -> {
                BindException ex = (BindException) result.getResolvedException();
                assertThat(ex.getBindingResult().getFieldError().getDefaultMessage()).isEqualTo(
                    "비밀번호의 길이는 8자 이상, 20자 이하 이내입니다.");
            })
            .andReturn();
    }

    @DisplayName("사용자 정보 업데이트 테스트0 : 성공")
    @Test
    void updateMember() {
    }

    @DisplayName("회원가입 테스트2 : 검증 실패")
    @Test
    void deleteMember() {
    }
}