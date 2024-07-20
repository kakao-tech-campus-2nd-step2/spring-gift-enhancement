package gift.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import gift.dto.LoginRequest;
import gift.dto.OptionRequest;
import gift.service.ProductOptionService;
import gift.service.auth.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProductOptionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProductOptionService productOptionService;
    @Autowired
    private AuthService authService;
    private String memberToken;

    @BeforeEach
    @DisplayName("이용자의 토큰 값 세팅하기")
    void setBaseData() {
        var memberLoginRequest = new LoginRequest("member@naver.com", "password");
        memberToken = authService.login(memberLoginRequest).token();
    }

    @Test
    @DisplayName("잘못된 수량으로 된 오류 상품 옵션 생성하기")
    void failOptionAdd() throws Exception {
        //given
        var postRequest = post("/api/products/1/options/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + memberToken)
                .content(objectMapper.writeValueAsString(new OptionRequest("기본", 0)));
        //when
        var result = mockMvc.perform(postRequest);
        //then
        result.andExpect(status().isBadRequest())
                .andExpect(content().string("수량은 최소 1개 이상, 1억개 미만입니다."));
    }

    @Test
    @DisplayName("빈 이름을 가진 오류 상품 옵션 생성하기")
    void failOptionAddWithEmptyName() throws Exception {
        //given
        var postRequest = post("/api/products/1/options/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + memberToken)
                .content(objectMapper.writeValueAsString(new OptionRequest("", 1000)));
        //when
        var result = mockMvc.perform(postRequest);
        //then
        result.andExpect(status().isBadRequest())
                .andExpect(content().string("이름의 길이는 최소 1자 이상이어야 합니다."));
    }

    @Test
    @DisplayName("이름의 길이가 50초과인 오류 상품 생성하기")
    void failOptionAddWithLengthOver50() throws Exception {
        //given
        var postRequest = post("/api/products/1/options/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + memberToken)
                .content(objectMapper.writeValueAsString(new OptionRequest("aaaaaaaaaaaaaaaaaabbbbbbbbbbbbcccccccccccccccddddddddddddddddddddwwwwwwwwwwqqqqqqqqqqqqqqq", 1000)));
        //when
        var result = mockMvc.perform(postRequest);
        //then
        result.andExpect(status().isBadRequest())
                .andExpect(content().string("이름의 길이는 50자를 초과할 수 없습니다."));
    }

    @Test
    @DisplayName("정상 상품 옵션 생성하기")
    void successOptionAdd() throws Exception {
        //given
        var postRequest = post("/api/products/1/options/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + memberToken)
                .content(objectMapper.writeValueAsString(new OptionRequest("Large", 1500)));
        //when
        var result = mockMvc.perform(postRequest);
        //then
        var createdResult = result.andExpect(status().isCreated()).andReturn();

        deleteOptionWithCreatedHeader(1L, createdResult);
    }

    @Test
    @DisplayName("존재하지 않는 상품에 대한 옵션 생성하기")
    void failOptionWithNotExistProductId() throws Exception {
        //given
        var postRequest = post("/api/products/1000/options/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + memberToken)
                .content(objectMapper.writeValueAsString(new OptionRequest("Large", 1500)));
        //when
        var result = mockMvc.perform(postRequest);
        //then
        result.andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("정상 옵션 생성하기 - 특수문자 포함")
    void successAddProductOptionWithSpecialChar() throws Exception {
        //given
        var postRequest = post("/api/products/1/options/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + memberToken)
                .content(objectMapper.writeValueAsString(new OptionRequest("햄버거()[]+-&/_", 1000)));
        //when
        var result = mockMvc.perform(postRequest);
        //then
        var createdResult = result.andExpect(status().isCreated()).andReturn();

        deleteOptionWithCreatedHeader(1L, createdResult);
    }

    @Test
    @DisplayName("정상 상품 생성하기 - 공백 포함")
    void successAddProductOptionWithEmptySpace() throws Exception {
        //given
        var postRequest = post("/api/products/1/options/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + memberToken)
                .content(objectMapper.writeValueAsString(new OptionRequest("햄버거 햄버거 햄버거", 1000)));
        //when
        var result = mockMvc.perform(postRequest);
        //then
        var createdResult = result.andExpect(status().isCreated()).andReturn();

        deleteOptionWithCreatedHeader(1L, createdResult);

    }

    @Test
    @DisplayName("오류 상품 생성하기 - 허용되지 않은 특수문자 포함")
    void addProductOptionFailWithSpecialChar() throws Exception {
        //given
        var postRequest = post("/api/products/1/options/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + memberToken)
                .content(objectMapper.writeValueAsString(new OptionRequest("햄버거()[]+-&/_**", 1000)));
        //when
        var result = mockMvc.perform(postRequest);
        //then
        result.andExpect(status().isBadRequest())
                .andExpect(content().string("허용되지 않은 형식의 이름입니다."));
    }

    private void deleteOptionWithCreatedHeader(Long productId, MvcResult mvcResult) {
        var location = mvcResult.getResponse().getHeader("Location");
        var optionId = location.replaceAll("/api/products/" + productId + "/options/", "");
        productOptionService.deleteOption(productId, Long.parseLong(optionId));
    }
}
