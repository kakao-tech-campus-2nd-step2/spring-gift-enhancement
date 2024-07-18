package gift.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import gift.dto.CategoryResponse;
import gift.service.CategoryService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


//@WebMvcTest(CategoryController.class)
class CategoryControllerTest {
    /*
    @Autowired
    CategoryController categoryController;

    @InjectMocks
    CategoryService categoryService;

    @Test
    @DisplayName("카테고리 정상 조회 확인")
    void getAllCategories(){
        //given

        BDDMockito.given(categoryService.readAll()).willReturn(
            List.of(getCategoryResponse(0), getCategoryResponse(1), getCategoryResponse(2)));

        //when
        List<CategoryResponse> actual = categoryController.getCategories().getBody();

        //then
        assertThat(actual).hasSize(3);
        assertThat(actual.get(0)).isEqualTo(getCategoryResponse(0));
        assertThat(actual.get(1)).isEqualTo(getCategoryResponse(1));
        assertThat(actual.get(2)).isEqualTo(getCategoryResponse(2));

    }

    private static CategoryResponse getCategoryResponse(long id) {
        return new CategoryResponse(id, "happy"+id, "#123"+id, "hah.url"+id, "");
    }

     */


}