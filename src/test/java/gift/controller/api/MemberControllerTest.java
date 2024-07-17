package gift.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import gift.dto.request.MemberRequest;
import gift.dto.response.TokenResponse;
import gift.service.MemberService;
import gift.service.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MemberController.class)
@DisplayName("멤버 컨트롤러 단위테스트")
class MemberControllerTest {

    private static final String REGISTER_URL = "/members/register";
    private static final String LOGIN_URL = "/members/login";
    @MockBean
    JpaMetamodelMappingContext jpaMetamodelMappingContext;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private MemberService memberService;
    @MockBean
    private TokenService tokenService;

    @Test
    @DisplayName("회원가입")
    void registerMember() throws Exception {
        //Given
        MemberRequest registerRequest = new MemberRequest("member1@gmail.com", "1234");

        when(memberService.register(registerRequest)).thenReturn(1L);
        when(tokenService.generateToken(1L)).thenReturn(new TokenResponse("JSH"));

        //When
        mockMvc.perform(MockMvcRequestBuilders
                        .post(REGISTER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                //Then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("token").exists()
                );
    }

    @Test
    @DisplayName("로그인")
    void loginMember() throws Exception {
        //Given
        MemberRequest loginRequest = new MemberRequest("member1@gmail.com", "1234");
        when(memberService.login(loginRequest)).thenReturn(1L);
        when(tokenService.generateToken(1L)).thenReturn(new TokenResponse("secret"));

        //When
        mockMvc.perform(MockMvcRequestBuilders
                        .post(LOGIN_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                //Then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("token").exists()
                );
    }
}
