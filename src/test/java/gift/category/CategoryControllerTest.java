package gift.category;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {

    @InjectMocks
    private CategoryController categoryController;
    @Mock
    private CategoryService categoryService;

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    public void addCategory() throws Exception {
        //given
        CategoryRequest categoryRequest = new CategoryRequest("category", "#6c95d1",
            "https://gift-s.kakaocdn.net/dn/gift/images/m640/dimm_theme.png", "");
        //String contents = new ObjectMapper().writeValueAsString(categoryRequest);

        //when
        ResultActions resultAction = mockMvc.perform(
            MockMvcRequestBuilders.post("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(categoryRequest)));

        //then
        MvcResult mvcResult = resultAction.andExpect(status().isCreated())
            .andDo(print())
            .andReturn();

        System.out.println("MvcResult :: " + mvcResult.getResponse().getContentAsString());
    }


}
