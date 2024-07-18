package gift.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import gift.dto.LoginRequest;
import gift.dto.ProductCategoryRequest;
import gift.service.auth.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
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
    @DisplayName("잘못된 색상코드로 된 카테고리 생성하기")
    void failCategoryAddWithWrongColorPattern() throws Exception {
        //given
        var postRequest = post("/api/categories/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + memberToken)
                .content(objectMapper.writeValueAsString(new ProductCategoryRequest("상품카테고리", "상품설명", "#11111", "image")));
        //when
        var result = mockMvc.perform(postRequest);
        //then
        result.andExpect(status().isBadRequest())
                .andExpect(content().string("허용되지 않은 형식의 색상코드입니다."));
    }

    @Test
    @DisplayName("카테고리 이미지는 공백이 입력되면 안된다")
    void failCategoryAddWithBlankCategoryImage() throws Exception {
        //given
        var postRequest = post("/api/categories/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + memberToken)
                .content(objectMapper.writeValueAsString(new ProductCategoryRequest("상품카테고리", "상품설명", "#111111", "")));
        //when
        var result = mockMvc.perform(postRequest);
        //then
        result.andExpect(status().isBadRequest())
                .andExpect(content().string("카테고리 설명 이미지는 필수로 입력해야 합니다."));
    }

    @Test
    @DisplayName("카테고리 이름은 공백이 입력되면 안된다")
    void failCategoryAddWithBlankCategoryName() throws Exception {
        //given
        var postRequest = post("/api/categories/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + memberToken)
                .content(objectMapper.writeValueAsString(new ProductCategoryRequest("", "상품설명", "#111111", "이미지")));
        //when
        var result = mockMvc.perform(postRequest);
        //then
        result.andExpect(status().isBadRequest())
                .andExpect(content().string("이름의 길이는 최소 1자 이상이어야 합니다."));
    }

    @Test
    @DisplayName("카테고리 설명은 공백이 입력되면 안된다")
    void failCategoryAddWithBlankCategoryDescription() throws Exception {
        //given
        var postRequest = post("/api/categories/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + memberToken)
                .content(objectMapper.writeValueAsString(new ProductCategoryRequest("상품카테고리", "", "#111111", "이미지")));
        //when
        var result = mockMvc.perform(postRequest);
        //then
        result.andExpect(status().isBadRequest())
                .andExpect(content().string("카테고리 설명은 필수로 입력해야 합니다."));
    }

    @Test
    @DisplayName("정상 상품 카테고리 생성")
    void successCategoryAdd() throws Exception {
        //given
        var postRequest = post("/api/categories/add")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + memberToken)
                .content(objectMapper.writeValueAsString(new ProductCategoryRequest("상품카테고리", "상품설명", "#111111", "이미지")));
        //when
        var result = mockMvc.perform(postRequest);
        //then
        result.andExpect(status().isCreated());
    }
}
