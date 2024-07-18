package gift.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import gift.dto.ProductOptionRequest;
import gift.service.ProductOptionService;
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
class ProductOptionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProductOptionService productOptionService;

    @Test
    @DisplayName("잘못된 가격으로 된 오류 상품 옵션 생성하기")
    void failOptionAdd() throws Exception {
        //given
        var postRequest = post("/api/options/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new ProductOptionRequest(1L, "기본", -1000)));
        //when
        var result = mockMvc.perform(postRequest);
        //then
        result.andExpect(status().isBadRequest())
                .andExpect(content().string("추가 금액은 0보다 크거나 같아야 합니다."));
    }

    @Test
    @DisplayName("빈 이름을 가진 오류 상품 옵션 생성하기")
    void failOptionAddWithEmptyName() throws Exception {
        //given
        var postRequest = post("/api/options/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new ProductOptionRequest(1L, "", 1000)));
        //when
        var result = mockMvc.perform(postRequest);
        //then
        result.andExpect(status().isBadRequest())
                .andExpect(content().string("이름의 길이는 최소 1자 이상이어야 합니다."));
    }

    @Test
    @DisplayName("정상 상품 옵션 생성하기")
    void successOptionAdd() throws Exception {
        //given
        var postRequest = post("/api/options/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new ProductOptionRequest(1L, "Large", 1500)));
        //when
        var result = mockMvc.perform(postRequest);
        //then
        var createdResult = result.andExpect(status().isCreated()).andReturn();

        var location = createdResult.getResponse().getHeader("Location");
        var optionId = location.replaceAll("/api/options/", "");
        deleteOption(Long.parseLong(optionId));
    }

    @Test
    @DisplayName("존재하지 않는 상품에 대한 옵션 생성하기")
    void failOptionWithNotExistProductId() throws Exception {
        //given
        var postRequest = post("/api/options/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new ProductOptionRequest(100L, "Large", 1500)));
        //when
        var result = mockMvc.perform(postRequest);
        //then
        result.andExpect(status().isNotFound());
    }

    private void deleteOption(Long id) {
        productOptionService.deleteOption(id);
    }
}
