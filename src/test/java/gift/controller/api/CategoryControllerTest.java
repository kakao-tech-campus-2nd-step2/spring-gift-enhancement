package gift.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import gift.dto.request.AddCategoryRequest;
import gift.dto.response.CategoryIdResponse;
import gift.dto.response.CategoryResponse;
import gift.service.CategoryService;
import gift.service.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CategoryController.class)
@DisplayName("카테고리 컨트롤러 단위테스트")
class CategoryControllerTest {

    private static final String URL = "/api/categories";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CategoryService categoryService;
    @MockBean
    private TokenService tokenService;
    @MockBean
    private JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Test
    @DisplayName("카테고리 추가")
    void addCategory() throws Exception {
        //Given
        AddCategoryRequest addCategoryRequest = new AddCategoryRequest("교환권", "색", "이미지주소", "설명");
        CategoryIdResponse addedCategoryIdResponse = new CategoryIdResponse(1L);

        //When
        when(categoryService.addCategory(addCategoryRequest)).thenReturn(addedCategoryIdResponse);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addCategoryRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(1L)
                );
    }

    @Test
    @DisplayName("모든 카테고리 조회")
    void getCategories() throws Exception {
        //Given
        List<CategoryResponse> categoryResponses = new ArrayList<>();
        categoryResponses.add(new CategoryResponse(1L, "교환권", "색", "이미지주소", "설명"));
        categoryResponses.add(new CategoryResponse(2L, "백화점", "색", "이미지주소", "설명"));

        //When
        when(categoryService.getAllCategoryResponses()).thenReturn(categoryResponses);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("[0].id").value(1L),
                        jsonPath("[1].id").value(2L)
                );
    }
}
