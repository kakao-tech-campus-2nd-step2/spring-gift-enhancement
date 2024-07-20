package gift.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import gift.dto.ProductDto;
import gift.exception.NonIntegerPriceException;
import gift.model.Category;
import gift.model.Option;
import gift.model.Product;
import gift.security.LoginMemberArgumentResolver;
import gift.service.CategoryService;
import gift.service.ProductService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;


    @MockBean
    private CategoryService categoryService;

    @MockBean
    private LoginMemberArgumentResolver loginMemberArgumentResolver;

    @Autowired
    private ObjectMapper objectMapper;


    private Product product;
    private Category category;
    private List<Product> productList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        product = new Product("상품", 10000, "image.jpg");
        category = new Category(1L, "카테고리", List.of(product));
        product.setId(1L);
        product.setCategory(category);
        product.setOptionList(List.of(new Option("option", 1)));
        productList.add(product);
    }

    @Test
    void getAllProductsTest() throws Exception {
        // given
        given(productService.getAllProducts()).willReturn(productList);

        // when & then
        mockMvc.perform(get("/products/all").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product))).andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value("OK"))
            .andExpect(jsonPath("$.message").value("상품 전체 조회 성공"))
            .andExpect(jsonPath("$.httpStatus").value("OK"));
    }

    @Test
    void addProductTest() throws Exception, NonIntegerPriceException {
        // given
        var productDto = new ProductDto(product.getName(), product.getPrice(),
            product.getImageUrl(), category.getId());
        given(productService.createProduct(productDto)).willReturn(product);

        // when & then
        mockMvc.perform(post("/product/add").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto)))
            .andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/"));
    }

    @Test
    void updateProductTest() throws Exception {
        // given
        var newProduct = new Product(product.getId(), product.getName(), product.getPrice(),
            product.getImageUrl(), product.getWishList(), product.getCategory(), product.getOptionList());
        newProduct.setPrice(2000);
        given(productService.updateProduct(newProduct)).willReturn(newProduct);

        // when & then
        mockMvc.perform(post("/product/update").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newProduct)))
            .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/"));
    }

    @Test
    void deleteProductTest() throws Exception {
        // given
        given(productService.deleteProduct(any(Long.class))).willReturn(true);

        // when & then
        mockMvc.perform(
                get("/product/delete/{id}", product.getId()).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/"));
    }

    @Test
    void deleteProductFormFailureTest() throws Exception {
        // given
        given(productService.deleteProduct(product.getId())).willReturn(false);

        // when & then
        mockMvc.perform(get("/product/delete/{id}", product.getId()))
            .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/"))
            .andExpect(flash().attributeCount(0));
    }
}