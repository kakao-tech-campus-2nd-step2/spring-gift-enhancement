package gift;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import gift.controller.CategoryController;
import gift.dto.CategoryRequestDto;
import gift.dto.CategoryResponseDto;
import gift.entity.Category;
import gift.service.CategoryService;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
public class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private MockMvc mockMvc;

    @Test
    @DisplayName("전건 조회 테스트")
    public void getCategoriesTest() throws Exception {
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto(1L, "교환권");
        when(categoryService.findAll()).thenReturn(Arrays.asList(new Category("교환권")));

        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();

        mockMvc.perform(get("/api/categories"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name").value("교환권"));
    }

    @Test
    @DisplayName("카테고리 추가 테스트")
    public void addCategoryTest() throws Exception {
        CategoryRequestDto categoryRequestDto = new CategoryRequestDto(1L, "교환권");
        Category category = new Category("교환권");
        when(categoryService.existsByName(any(String.class))).thenReturn(false);
        when(categoryService.save(any(Category.class))).thenReturn(category);

        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();

        mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"교환권\"}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("교환권"));
    }

    @Test
    @DisplayName("카테고리 수정 테스트")
    public void updateCategoryTest() throws Exception {
        CategoryRequestDto categoryRequestDto = new CategoryRequestDto(1L, "기프티콘");
        Category category = new Category("기프티콘");
        when(categoryService.findById(any(Long.class))).thenReturn(java.util.Optional.of(new Category("교환권")));
        when(categoryService.save(any(Category.class))).thenReturn(category);

        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();

        mockMvc.perform(put("/api/categories/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"기프티콘\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("기프티콘"));
    }


}
