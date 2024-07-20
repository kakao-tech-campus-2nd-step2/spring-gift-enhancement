package gift.domain.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import gift.domain.dto.request.OptionAddRequest;
import gift.domain.dto.request.ProductAddRequest;
import gift.domain.dto.response.CategoryResponse;
import gift.domain.dto.response.OptionResponse;
import gift.domain.dto.response.ProductResponse;
import gift.domain.entity.Category;
import gift.domain.entity.Option;
import gift.domain.service.MemberService;
import gift.domain.service.ProductService;
import gift.global.util.JwtUtil;
import gift.utilForTest.MockObjectSupplier;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ProductController.class)
public class ProductControllerValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @MockBean
    private MemberService memberService;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    @DisplayName("[UnitTest] 상품 추가 이름검증: 이름이 문제 없는 경우")
    void addProduct() throws Exception {
        //given
        ProductAddRequest validRequest = new ProductAddRequest(
            "ValidName",
            1000,
            "http://example.com/image.jpg",
            1L,
            List.of(OptionAddRequest.of(MockObjectSupplier.get(Option.class))));
        ProductResponse productResponse = new ProductResponse(
            1L,
            validRequest.name(),
            validRequest.price(),
            validRequest.imageUrl(),
            CategoryResponse.of(MockObjectSupplier.get(Category.class)),
            List.of(OptionResponse.of(MockObjectSupplier.get(Option.class))));

        given(productService.addProduct(any(ProductAddRequest.class))).willReturn(productResponse);

        //when
        mockMvc.perform(post("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(validRequest))
        )

        //TODO: 카테고리, 옵션도 검증하기
        //then
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.created-product.name").value("ValidName"))
            .andExpect(jsonPath("$.created-product.price").value(1000))
            .andExpect(jsonPath("$.created-product.image-url").value("http://example.com/image.jpg"))
            .andExpect(header().string("Location", "/api/products/1"));
    }

    @Test
    @DisplayName("[UnitTest/Fail] 상품 추가 이름검증: 검증이 실패하는 3가지 경우")
    void addProduct_ValidationFails() throws Exception {
        //given
        String[] invalidRequestName = {
            "toooo long product name",
            "SpecialChar $*#",
            "name is 카카오" };
        String[] expected = {
            "공백을 포함하여 최대 15자까지 입력할 수 있습니다.",
            "(), [], +, -, &, /, _ 이외의 특수 문자는 사용할 수 없습니다.",
            "'카카오'가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있습니다."};

        for (int i = 0; i < invalidRequestName.length; i++) {
            //when
            mockMvc.perform(post("/api/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(new ProductAddRequest(
                        invalidRequestName[i],
                        1000,
                        "http://example.com/image.jpg",
                        1L,
                        List.of(OptionAddRequest.of(MockObjectSupplier.get(Option.class))))))
                )

            //TODO: 카테고리, 옵션도 검증하기
            //then
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("name: " + expected[i]));
        }
    }
}
