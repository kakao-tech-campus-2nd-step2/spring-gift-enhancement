package gift.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import gift.category.api.CategoryController;
import gift.category.application.CategoryService;
import gift.category.dto.CategoryRequest;
import gift.category.dto.CategoryResponse;
import gift.category.entity.Category;
import gift.category.util.CategoryMapper;
import gift.global.error.CustomException;
import gift.global.error.ErrorCode;
import gift.global.security.JwtFilter;
import gift.global.security.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(CategoryController.class)
@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @InjectMocks
    private JwtFilter jwtFilter;
    @MockBean
    private JwtUtil jwtUtil;
    @MockBean
    private CategoryService categoryService;
    private final String bearerToken = "Bearer token";

    @Test
    @DisplayName("카테고리 전체 조회 기능 테스트")
    void getAllCategories() throws Exception {
        List<CategoryResponse> response = new ArrayList<>();
        Category category1 = new Category.CategoryBuilder()
                .setName("상품권")
                .setColor("#ffffff")
                .setImageUrl("https://product-shop.com")
                .setDescription("")
                .build();
        Category category2 = new Category.CategoryBuilder()
                .setName("교환권")
                .setColor("#123456")
                .setImageUrl("https://product-shop.com")
                .setDescription("")
                .build();
        response.add(CategoryMapper.toResponseDto(category1));
        response.add(CategoryMapper.toResponseDto(category2));

        String responseJson = objectMapper.writeValueAsString(response);
        given(categoryService.getAllCategories()).willReturn(response);

        mockMvc.perform(get("/api/categories")
                .header(HttpHeaders.AUTHORIZATION, bearerToken))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson))
                .andDo(print());

        verify(categoryService).getAllCategories();
    }

    @Test
    @DisplayName("카테고리 상세 조회 기능 테스트")
    void getCategory() throws Exception {
        CategoryResponse response = CategoryMapper.toResponseDto(
                new Category.CategoryBuilder()
                        .setName("상품권")
                        .setColor("#ffffff")
                        .setImageUrl("https://product-shop.com")
                        .setDescription("")
                        .build()
        );
        Long responseId = 1L;
        String responseJson = objectMapper.writeValueAsString(response);
        given(categoryService.getCategoryByIdOrThrow(any())).willReturn(response);

        mockMvc.perform(get("/api/categories/{id}", responseId)
                        .header(HttpHeaders.AUTHORIZATION, bearerToken))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson))
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.name").value(response.name()))
                .andExpect(jsonPath("$.color").value(response.color()))
                .andExpect(jsonPath("$.imageUrl").value(response.imageUrl()))
                .andExpect(jsonPath("$.description").value(response.description()));

        verify(categoryService).getCategoryByIdOrThrow(responseId);
    }

    @Test
    @DisplayName("카테고리 상세 조회 실패 테스트")
    void getCategoryFailed() throws Exception {
        Long categoryId = 1L;
        Throwable exception = new CustomException(ErrorCode.CATEGORY_NOT_FOUND);
        given(categoryService.getCategoryByIdOrThrow(any())).willThrow(exception);

        mockMvc.perform(get("/api/categories/{id}", categoryId)
                        .header(HttpHeaders.AUTHORIZATION, bearerToken))
                .andExpect(status().isNotFound())
                .andExpect(content().string(exception.getMessage()));

        verify(categoryService).getCategoryByIdOrThrow(categoryId);
    }

    @Test
    @DisplayName("카테고리 추가 기능 테스트")
    void addCategory() throws Exception {
        CategoryRequest request = new CategoryRequest(
                "상품권",
                "#ffffff",
                "https://product-shop.com",
                ""
        );
        CategoryResponse response = CategoryMapper.toResponseDto(
                CategoryMapper.toEntity(request)
        );
        String requestJson = objectMapper.writeValueAsString(request);
        String responseJson = objectMapper.writeValueAsString(response);
        given(categoryService.createCategory(any())).willReturn(response);

        mockMvc.perform(post("/api/categories")
                        .header(HttpHeaders.AUTHORIZATION, bearerToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson))
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.name").value(response.name()))
                .andExpect(jsonPath("$.color").value(response.color()))
                .andExpect(jsonPath("$.imageUrl").value(response.imageUrl()))
                .andExpect(jsonPath("$.description").value(response.description()));

        verify(categoryService).createCategory(request);
    }

    @Test
    @DisplayName("카테고리 삭제 기능 테스트")
    void deleteCategory() throws Exception {
        Long categoryId = 1L;
        given(categoryService.deleteCategoryById(any())).willReturn(categoryId);

        mockMvc.perform(delete("/api/categories/{id}", categoryId)
                        .header(HttpHeaders.AUTHORIZATION, bearerToken))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(categoryId)));

        verify(categoryService).deleteCategoryById(categoryId);
    }

    @Test
    @DisplayName("카테고리 삭제 실패 테스트")
    void deleteCategoryFailed() throws Exception {
        Long categoryId = 1L;
        Throwable exception = new CustomException(ErrorCode.CATEGORY_NOT_FOUND);
        given(categoryService.deleteCategoryById(any())).willThrow(exception);

        mockMvc.perform(delete("/api/categories/{id}", categoryId)
                        .header(HttpHeaders.AUTHORIZATION, bearerToken))
                .andExpect(status().isNotFound())
                .andExpect(content().string(exception.getMessage()));
    }

    @Test
    @DisplayName("카테고리 수정 기능 테스트")
    void updateCategory() throws Exception {
        Long categoryId = 1L;
        CategoryRequest request = new CategoryRequest(
                "상품권",
                "#ffffff",
                "https://product-shop.com",
                ""
        );
        String requestJson = objectMapper.writeValueAsString(request);
        given(categoryService.updateCategory(categoryId, request)).willReturn(categoryId);

        mockMvc.perform(patch("/api/categories/{id}", categoryId)
                        .header(HttpHeaders.AUTHORIZATION, bearerToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(categoryId)));
    }

    @Test
    @DisplayName("카테고리 수정 실패 테스트")
    void updateCategoryFailed() throws Exception {
        Long categoryId = 1L;
        CategoryRequest request = new CategoryRequest(
                "상품권",
                "#ffffff",
                "https://product-shop.com",
                ""
        );
        String requestJson = objectMapper.writeValueAsString(request);
        Throwable exception = new CustomException(ErrorCode.CATEGORY_NOT_FOUND);
        given(categoryService.updateCategory(categoryId, request)).willThrow(exception);

        mockMvc.perform(patch("/api/categories/{id}", categoryId)
                        .header(HttpHeaders.AUTHORIZATION, bearerToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound())
                .andExpect(content().string(exception.getMessage()));
    }

}