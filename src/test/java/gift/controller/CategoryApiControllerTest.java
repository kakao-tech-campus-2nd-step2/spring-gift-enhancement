package gift.controller;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import gift.administrator.category.CategoryApiController;
import gift.administrator.category.CategoryDTO;
import gift.administrator.category.CategoryService;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryApiControllerTest {

    private final CategoryService categoryService = mock(CategoryService.class);
    private MockMvc mvc;
    private ObjectMapper objectMapper;
    private CategoryApiController categoryApiController;

    @BeforeEach
    void beforeEach() {
        categoryApiController = new CategoryApiController(categoryService);
        mvc = MockMvcBuilders.standaloneSetup(categoryApiController)
            .defaultResponseCharacterEncoding(UTF_8)
            .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("모든 카테고리 가져오기")
    void getAllCategories() throws Exception {
        //given
        CategoryDTO categoryDTO = new CategoryDTO(1L, "상품권", "#ff11ff", "image.jpg", "");
        CategoryDTO categoryDTO1 = new CategoryDTO(2L, "인형", "#dd11ff", "image.jpg", "none");
        given(categoryService.getAllCategories()).willReturn(
            Arrays.asList(categoryDTO, categoryDTO1));

        //when
        ResultActions resultActions = mvc.perform(
            MockMvcRequestBuilders.get("/api/categories")
                .contentType("application/json"));

        //then
        resultActions.andExpect(status().isOk())
            .andExpect(content().json(
                objectMapper.writeValueAsString(Arrays.asList(categoryDTO, categoryDTO1))));
    }

    @Test
    @DisplayName("아이디에 따른 카테고리 가져오기")
    void getCategoryById() throws Exception {
        //given
        CategoryDTO categoryDTO = new CategoryDTO(1L, "상품권", "#ff11ff", "image.jpg", "");
        given(categoryService.getCategoryById(1L)).willReturn(categoryDTO);

        //when
        ResultActions resultActions = mvc.perform(
            MockMvcRequestBuilders.get("/api/categories/1")
                .contentType("application/json"));

        //then
        resultActions.andExpect(status().isOk())
            .andExpect(content().json(
                objectMapper.writeValueAsString(categoryDTO)));
    }

    @Test
    @DisplayName("카테고리 저장")
    void addCategory() throws Exception {
        //given
        CategoryDTO categoryDTO = new CategoryDTO(1L, "상품권", "#ff11ff", "image.jpg", "");
        doNothing().when(categoryService).existsByNamePutResult(any(), any());
        given(categoryService.addCategory(categoryDTO)).willReturn(categoryDTO);

        //when
        ResultActions resultActions = mvc.perform(
            MockMvcRequestBuilders.post("/api/categories")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(categoryDTO)))
            .andDo(print());

        //then
        resultActions.andExpect(status().isCreated());
    }

    @Test
    @DisplayName("카테고리 저장시 컬러코드가 valid하지 않은 값 입력")
    void addCategoryNotValid() throws Exception {
        //given
        CategoryDTO categoryDTO = new CategoryDTO(1L, "상품권", "#", "image.jpg", "");
        doNothing().when(categoryService).existsByNamePutResult(any(), any());

        //when
        ResultActions resultActions = mvc.perform(
            MockMvcRequestBuilders.post("/api/categories")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(categoryDTO)));

        //then
        resultActions.andExpect(status().isBadRequest())
            .andExpect(content().string(org.hamcrest.Matchers.containsString("컬러코드가 아닙니다.")));
    }

    @Test
    @DisplayName("카테고리 수정")
    void updateCategory() throws Exception {
        //given
        CategoryDTO categoryDTO = new CategoryDTO(1L, "상품권", "#ff11ff", "image.jpg", "");
        doNothing().when(categoryService).existsByNameAndIdPutResult(anyString(), anyLong(), any());
        given(categoryService.updateCategory(any(CategoryDTO.class))).willReturn(categoryDTO);

        //when
        ResultActions resultActions = mvc.perform(
            MockMvcRequestBuilders.put("/api/categories/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(categoryDTO)));

        //then
        resultActions.andExpect(status().isOk())
            .andExpect(content().json(
                objectMapper.writeValueAsString(categoryDTO)));
    }

    @Test
    @DisplayName("카테고리 삭제")
    void deleteCategory() throws Exception {
        //given
        CategoryDTO categoryDTO = new CategoryDTO(1L, "상품권", "#ff11ff", "image.jpg", "");
        given(categoryService.getCategoryById(1L)).willReturn(categoryDTO);
        doNothing().when(categoryService).deleteCategory(1L);

        //when
        ResultActions resultActions = mvc.perform(
            MockMvcRequestBuilders.delete("/api/categories/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(categoryDTO)));

        //then
        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("카테고리 삭제 존재하지 않는 아이디라 실패해서 NotFoundException 던짐")
    void deleteCategoryFailed() throws Exception {
        //given
        doThrow(new NotFoundException()).when(categoryService).deleteCategory(1L);

        //when

        //then
        assertThatThrownBy(() -> categoryApiController.deleteCategory(1L)).isInstanceOf(
            NotFoundException.class);
    }
}
