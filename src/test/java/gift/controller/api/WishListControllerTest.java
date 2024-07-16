package gift.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import gift.dto.request.WishListRequest;
import gift.dto.response.WishProductResponse;
import gift.interceptor.AuthInterceptor;
import gift.service.TokenService;
import gift.service.WishListService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = WishListController.class)
@DisplayName("위시 컨트롤러 단위테스트")
class WishListControllerTest {

    private static final String URL = "/api/wishlist";
    @MockBean
    private TokenService tokenService;
    @MockBean
    private JpaMetamodelMappingContext jpaMetamodelMappingContext;
    @MockBean
    private AuthInterceptor authInterceptor;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private WishListService wishListService;

    @Test
    @DisplayName("위시리스트 상품 추가")
    void addProductToWishList() throws Exception {
        //Given
        when(authInterceptor.preHandle(any(), any(), any())).thenReturn(true);
        WishListRequest request = new WishListRequest(1L, 100);

        //When
        mockMvc.perform(MockMvcRequestBuilders.post(URL)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer validTokenValue")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                //Then
                .andExpect(
                        status().isCreated()
                );
    }

    @Test
    @DisplayName("위시리스트 상품 조회")
    void getWishProducts() throws Exception {
        //Given
        when(authInterceptor.preHandle(any(), any(), any())).thenReturn(true);
        Page<WishProductResponse> page = new PageImpl<>(List.of(
                new WishProductResponse(1L, "product1", 100, "img", 1000),
                new WishProductResponse(2L, "product2", 400, "img", 2000)
        ));
        when(wishListService.getWishProductsByMemberId(any(), any())).thenReturn(page);

        //When
        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL))
                //Then
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.content[0].productName").value("product1"),
                        jsonPath("$.content[1].productName").value("product2")
                );
    }

    @Test
    @DisplayName("위시리스트 상품 수정")
    void updateWishProductAmount() throws Exception {
        //Given
        when(authInterceptor.preHandle(any(), any(), any())).thenReturn(true);
        WishListRequest updateRequest = new WishListRequest(1L, 1000);

        //When
        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                //Then
                .andExpect(
                        status().isOk()
                );
    }

    @Test
    @DisplayName("위시리스트 상품 삭제")
    void deleteWishProduct() throws Exception {
        //Given
        when(authInterceptor.preHandle(any(), any(), any())).thenReturn(true);

        //When
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(URL + "/1"))
                //Then
                .andExpect(
                        status().isOk()
                );
    }
}
