package gift.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gift.model.Category;
import gift.model.Member;
import gift.model.Product;
import gift.model.Wish;
import gift.security.LoginMemberArgumentResolver;
import gift.service.ProductService;
import gift.service.WishService;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(WishController.class)
class WishControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WishService wishService;

    @MockBean
    private ProductService productService;

    @MockBean
    private LoginMemberArgumentResolver loginMemberArgumentResolver;

    @Autowired
    private ObjectMapper objectMapper;

    private Wish wish;
    private Product product;
    private Member member;
    private Category category;

    @BeforeEach
    void setUp() {
        member = new Member("Email@test.com", "password");
        member.setId(1L);
        product = new Product("product", 10000, "image.jpg");
        category = new Category(1L, "category", Collections.singletonList(product));
        product.setCategory(category);
        wish = new Wish(product, member);
        product.setWishList(Collections.singletonList(wish));
        given(loginMemberArgumentResolver.supportsParameter(any())).willReturn(true);
        given(loginMemberArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(member);
    }

    @Test
    void getWishes() throws Exception {
        // given
        given(wishService.getWishesByMember(any(Member.class))).willReturn(
            Collections.singletonList(wish));

        // when & then
        mockMvc.perform(get("/wishes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(member)))
            .andExpect(status().isOk());
    }

}