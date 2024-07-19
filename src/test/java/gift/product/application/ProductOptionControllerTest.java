package gift.product.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import gift.product.service.ProductOptionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductOptionController.class)
@ActiveProfiles("test")
class ProductOptionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductOptionService productOptionService;

    @Test
    @DisplayName("ProductOptionController Option생성 테스트")
    void createProductOptionTest() throws Exception {
        //given
        final Long productId = 1L;
        final String requestURI = "/api/products/" + productId + "/options";
        final String requestBody = "{\n" +
                "  \"name\": \"optionName\",\n" +
                "  \"price\": 1000\n" +
                "}";

        given(productOptionService.createProductOption(eq(productId), any())).willReturn(1L);

        //when//then
        mockMvc.perform(post(requestURI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    @DisplayName("ProductOptionController Option수정 테스트")
    void modifyProductOptionTest() throws Exception {
        //given
        final Long productId = 1L;
        final Long optionId = 1L;
        final String requestURI = "/api/products/" + productId + "/options/" + optionId;
        final String requestBody = "{\n" +
                "  \"name\": \"optionName\",\n" +
                "  \"price\": 1000\n" +
                "}";

        //when//then
        mockMvc.perform(patch(requestURI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
        then(productOptionService).should().modifyProductOption(eq(productId), eq(optionId), any());
    }
}