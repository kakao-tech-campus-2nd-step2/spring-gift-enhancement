package gift.controller;

import gift.filter.AuthFilter;
import gift.filter.LoginFilter;
import gift.repository.token.TokenRepository;
import gift.service.OptionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OptionController.class)
class OptionControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    MockMvc mvc;

    @MockBean
    OptionService optionService;

    @MockBean
    TokenRepository tokenRepository;

    @Test
    @DisplayName("필터 통과 실패 테스트")
    void 필터_통과_실패_테스트() throws Exception {
        MockMvc mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(new AuthFilter(tokenRepository))
                .addFilter(new LoginFilter(tokenRepository))
                .build();

        mockMvc.perform(get("/options"))
                .andExpect(redirectedUrl("/home"))
                .andExpect(status().is3xxRedirection())
                .andDo(print());
    }

    @Test
    @DisplayName("카테고리 전체 조회 API 테스트")
    void 카테고리_전체_조회_API_테스트() throws Exception{
        //given

        //expected
        mvc.perform(post("/options/delete/{id}", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/products"))
                .andExpect(redirectedUrl("/products"))
                .andDo(print());

        verify(optionService, times(1)).deleteOneOption(1L);
    }


}