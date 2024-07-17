package gift.product.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import gift.auth.AuthenticationInterceptor;
import gift.auth.AuthorizationInterceptor;
import gift.member.service.JwtProvider;
import gift.product.service.CategoryService;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private AuthorizationInterceptor authorizationInterceptor;
    @MockBean
    private AuthenticationInterceptor authenticationInterceptor;
    @MockBean
    private JwtProvider jwtProvider;

    @Test
    @DisplayName("Category 생성 테스트[성공]")
    void createCategoryTest() throws Exception {
        // given
        given(categoryService.createCategory(any())).willReturn(1L);
        String requestURL = "/api/categories";
        String requestBody = "{\"name\":\"카테고리\", \"color\":\"색상\", \"imageUrl\":\"이미지 URL\", \"description\":\"설명\"}";
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "ADMIN");
        given(authorizationInterceptor.preHandle(any(), any(), any())).willReturn(true);
        given(authenticationInterceptor.preHandle(any(), any(), any())).willReturn(true);
        given(jwtProvider.tokenToClaims(any())).willReturn(claims);

        // when
        // then
        mockMvc.perform(post(requestURL)
                        .header("Authorization", "Bearer token")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }
}