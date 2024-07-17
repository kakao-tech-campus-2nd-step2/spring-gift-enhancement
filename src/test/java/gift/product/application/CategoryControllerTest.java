package gift.product.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import gift.auth.AuthorizationInterceptor;
import gift.member.persistence.MemberRepository;
import gift.member.service.JwtProvider;
import gift.product.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {
    @MockBean
    private CategoryService categoryService;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorizationInterceptor authorizationInterceptor;
    @MockBean
    private JwtProvider jwtProvider;
    @MockBean
    private MemberRepository memberRepository;

    @Test
    @DisplayName("Category 생성 테스트[성공]")
    void createCategoryTest() throws Exception {
        // given
        given(categoryService.createCategory(any())).willReturn(1L);
        String requestURL = "/api/categories";
        String requestBody = "{\"name\":\"카테고리\", \"color\":\"색상\", \"imageUrl\":\"이미지 URL\", \"description\":\"설명\"}";
        given(authorizationInterceptor.preHandle(any(), any(), any())).willReturn(true);

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