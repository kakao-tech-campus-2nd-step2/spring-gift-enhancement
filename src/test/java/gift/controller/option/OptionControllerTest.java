package gift.controller.option;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gift.controller.auth.AuthController;
import gift.controller.auth.LoginRequest;
import gift.controller.auth.Token;
import gift.controller.member.SignUpRequest;
import gift.service.MemberService;
import gift.service.OptionService;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

@WebMvcTest(OptionController.class)
class OptionControllerTest {

    @MockBean
    private OptionService optionService;
    @MockBean
    private MemberService memberService;
    @MockBean
    private AuthController authController;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Token token;

    public void setUp() {
        SignUpRequest request = new SignUpRequest("testEmail@kakao.com", "testPassword", "tester");
        memberService.save(request);
        token = authController.login(new LoginRequest(request.getEmail(), request.getPassword())).getBody();
    }

    @Test
    void getAllOptions() {
    }

    @Test
    void getAllOptionsByProductId() {
    }

    @Test
    void getOption() {
    }

    @DisplayName("옵션 생성 테스트0 : 성공")
    @Test
    void createOptionTest0() throws Exception {
        // given
        UUID productId = UUID.randomUUID();
        OptionRequest request = new OptionRequest("validName", 10);
        OptionResponse response = new OptionResponse(UUID.randomUUID(), "validName", 10, productId);
        doReturn(response).when(optionService).save(any(UUID.class), any(OptionRequest.class));

        // when
        ResultActions resultActions = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/options/" + productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        );

        resultActions.andExpect(status().isCreated())
            .andExpect(jsonPath("id", response.id()).exists())
            .andExpect(jsonPath("name", response.name()).exists())
            .andExpect(jsonPath("quantity", response.quantity()).exists())
            .andExpect(jsonPath("productId", response.productId()).exists());
    }

    @DisplayName("옵션 생성 테스트1 : 검증 실패")
    @Test
    void createOptionTest1() throws Exception {
    }

    @Test
    void updateOption() {
    }

    @Test
    void subtractOption() throws Exception {
        // given
        UUID optionId = UUID.randomUUID();
        Integer quantity = 10;
        OptionResponse response = new OptionResponse(UUID.randomUUID(), "validName", quantity - 1,
            optionId);
        doReturn(response).when(optionService).subtract(optionId, quantity);

        // when
        ResultActions resultActions = mockMvc.perform(
            MockMvcRequestBuilders.put("/api/options/" + optionId + "/subtract/" + quantity)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(response))
        );

        // then
        resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("id", response.id()).exists())
            .andExpect(jsonPath("name", response.name()).exists())
            .andExpect(jsonPath("quantity", response.quantity()).exists())
            .andExpect(jsonPath("productId", response.productId()).exists());
    }

    @Test
    void deleteOption() {
    }
}