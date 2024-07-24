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
import gift.domain.exception.ErrorCode;
import gift.domain.service.MemberService;
import gift.domain.service.ProductService;
import gift.global.WebConfig.Constants.Constraints;
import gift.global.WebConfig.Constants.Domain;
import gift.global.WebConfig.Constants.Domain.Product;
import gift.global.util.JwtUtil;
import gift.utilForTest.MockObjectSupplier;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
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

    private Option option;
    private Category category;

    @BeforeEach
    void beforeEach() {
        option = MockObjectSupplier.get(Option.class);
        category = MockObjectSupplier.get(Category.class);
    }

    private ProductAddRequest getAddRequest(String name, Long categoryId, List<Option> options) {
        return new ProductAddRequest(
            name,
            1000,
            "http://example.com/image.jpg", categoryId,
            OptionAddRequest.of(options));
    }

    @Test
    @DisplayName("[UnitTest] 상품 추가 이름검증: 이름이 문제 없는 경우")
    void addProduct() throws Exception {
        //given
        ProductAddRequest validRequest = getAddRequest("ValidName", 1L, List.of(option));
        ProductResponse productResponse = new ProductResponse(
            1L,
            validRequest.name(),
            validRequest.price(),
            validRequest.imageUrl(),
            CategoryResponse.of(category),
            List.of(OptionResponse.of(option)));

        given(productService.addProduct(any(ProductAddRequest.class))).willReturn(productResponse);

        //when
        mockMvc.perform(post("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(validRequest))
        )

        //then
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", "/api/products/1"))
            .andExpect(jsonPath("$.created-product.name").value(validRequest.name()))
            .andExpect(jsonPath("$.created-product.price").value(validRequest.price()))
            .andExpect(jsonPath("$.created-product.image-url").value(validRequest.imageUrl()))
            // 카테고리 검증
            .andExpect(jsonPath("$.created-product.category.id").value(category.getId()))
            .andExpect(jsonPath("$.created-product.category.name").value(category.getName()))
            .andExpect(jsonPath("$.created-product.category.color").value(category.getColor()))
            .andExpect(jsonPath("$.created-product.category.image-url").value(category.getImageUrl()))
            .andExpect(jsonPath("$.created-product.category.description").value(category.getDescription()))
            // 옵션 검증
            .andExpect(jsonPath("$.created-product.options[0].id").value(option.getId()))
            .andExpect(jsonPath("$.created-product.options[0].name").value(option.getName()))
            .andExpect(jsonPath("$.created-product.options[0].quantity").value(option.getQuantity()));
    }

    @Test
    @DisplayName("[UnitTest/Fail] 상품 추가 이름검증: 검증이 실패하는 3가지 경우")
    void addProduct_ProductNameValidationFails() throws Exception {
        //given
        String[] invalidRequestName = {
            "toooo long product name",
            "SpecialChar $*#",
            "name is 카카오" };
        String[] expected = {
            Product.NAME_LENGTH_INVALID_MSG,
            Constraints.DEFAULT_ALLOWED_SPECIAL_MSG,
            Product.NAME_INCLUDE_KAKAO_MSG };

        for (int i = 0; i < invalidRequestName.length; i++) {
            //when
            mockMvc.perform(post("/api/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(getAddRequest(invalidRequestName[i], 1L, List.of(option))))
                )

            //then
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("name: " + expected[i]))
                .andExpect(jsonPath("$.error-code").value(ErrorCode.FIELD_VALIDATION_FAIL.getCode()));
        }
    }

    @Test
    @DisplayName("[UnitTest/Fail] 상품 옵션 추가 이름검증: 검증이 실패하는 2가지 경우")
    void addProduct_ProductOptionNameValidationFails() throws Exception {
        //given
        String[] invalidRequestName = {
            "toooo long option name 01234567890123456789123456789123456789123456789",
            "SpecialChar $*#" };
        String[] expected = {
            Domain.Option.NAME_LENGTH_INVALID_MSG,
            Constraints.DEFAULT_ALLOWED_SPECIAL_MSG };

        for (int i = 0; i < invalidRequestName.length; i++) {
            //when
            Option opt = new Option(option.getProduct(), invalidRequestName[i], option.getQuantity());
            mockMvc.perform(post("/api/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(getAddRequest("ValidProduct", 1L, List.of(opt))))
                )

                //then
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("options[0].name: " + expected[i]))
                .andExpect(jsonPath("$.error-code").value(ErrorCode.FIELD_VALIDATION_FAIL.getCode()));
        }
    }

    @Test
    @DisplayName("[UnitTest] 상품 옵션 추가 수량검증: 수량이 범위 내에 있는 경우")
    void addProduct_ProductOptionQuantityValidate() throws Exception {
        //given
        Integer[] quantity = {
            Domain.Option.QUANTITY_RANGE_MIN,
            Domain.Option.QUANTITY_RANGE_MAX,
        };

        for (Integer q : quantity) {
            //given
            Option opt = new Option(option.getProduct(), option.getName(), q);
            ProductAddRequest validRequest = getAddRequest("ValidName", 1L, List.of(opt));
            ProductResponse productResponse = new ProductResponse(
                1L,
                validRequest.name(),
                validRequest.price(),
                validRequest.imageUrl(),
                CategoryResponse.of(category),
                List.of(OptionResponse.of(option)));
            given(productService.addProduct(any(ProductAddRequest.class))).willReturn(productResponse);

            //when
            option.setQuantity(q);
            mockMvc.perform(post("/api/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(validRequest))
                )

                //then
                .andDo(print())
                .andExpect(status().isCreated());
        }
    }

    @Test
    @DisplayName("[UnitTest/Fail] 상품 옵션 추가 수량검증: 수량이 범위를 벗어나는 경우")
    void addProduct_ProductOptionQuantityValidationFails() throws Exception {
        //given
        Integer[] quantity = {
            Domain.Option.QUANTITY_RANGE_MIN - 1,
            Domain.Option.QUANTITY_RANGE_MAX + 1
        };

        for (Integer q: quantity) {
            //when
            Option opt = new Option(option.getProduct(), option.getName(), q);
            mockMvc.perform(post("/api/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(getAddRequest("ValidProduct", 1L, List.of(opt))))
                )

                //then
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("options[0].quantity: " + Domain.Option.QUANTITY_INVALID_MSG))
                .andExpect(jsonPath("$.error-code").value(ErrorCode.FIELD_VALIDATION_FAIL.getCode()));
        }
    }
}
