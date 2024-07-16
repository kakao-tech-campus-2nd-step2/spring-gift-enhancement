package gift.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import gift.dto.request.AddProductRequest;
import gift.dto.request.UpdateProductRequest;
import gift.dto.response.AddedProductIdResponse;
import gift.dto.response.ProductResponse;
import gift.service.ProductService;
import gift.service.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
@DisplayName("상품 컨트롤러 단위테스트")
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    ProductService productService;
    @MockBean
    TokenService tokenService;
    @MockBean
    JpaMetamodelMappingContext jpaMetamodelMappingContext;

    private static final String URL = "/api/products";

    @Test
    @DisplayName("상품 조회")
    void getProducts() throws Exception {
        // Given
        List<ProductResponse> products = List.of(
                new ProductResponse(1L, "Product 1", 100, "img", "Cloth"),
                new ProductResponse(2L, "Product 2", 200, "img", "Food")
        );
        Page<ProductResponse> page = new PageImpl<>(products);
        when(productService.getProductResponses(any(Pageable.class))).thenReturn(page);

        // When
        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.content").exists(),
                        jsonPath("$.content[1].name").value("Product 2")
                );
    }


    @Test
    @DisplayName("상품 추가")
    void addProduct() throws Exception {
        // Given
        AddProductRequest addProductRequest = new AddProductRequest("Product1", 110, "img", 1L);
        AddedProductIdResponse addedProductIdResponse = new AddedProductIdResponse(1L);

        when(productService.addProduct(addProductRequest)).thenReturn(addedProductIdResponse);

        // When
        mockMvc.perform(MockMvcRequestBuilders
                        .post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addProductRequest)))
                //Then
                .andExpectAll(
                        status().isCreated(),
                        jsonPath("$.id").value(1L)
                );
    }


    @Test
    @DisplayName("상품 수정")
    void updateProduct() throws Exception {
        // Given
        UpdateProductRequest updateProductRequest = new UpdateProductRequest(1L, "changeProduct1", 110, "img", 1L);

        // When
        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateProductRequest)))
                //Then
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("상품 삭제")
    void deleteProduct() throws Exception {
        // Given
        Long deleteTargetId = 1L;

        // When
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(URL + "/{id}", deleteTargetId))
                //Then
                .andExpect(status().isOk());
        verify(productService, times(1)).deleteProduct(deleteTargetId);
    }
}
