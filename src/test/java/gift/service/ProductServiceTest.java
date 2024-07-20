package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.BDDMockito.*;

import gift.product.dto.ClientProductDto;
import gift.product.dto.ProductDto;
import gift.product.model.Category;
import gift.product.model.Option;
import gift.product.model.Product;
import gift.product.repository.CategoryRepository;
import gift.product.repository.OptionRepository;
import gift.product.repository.ProductRepository;
import gift.product.service.ProductService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class ProductServiceTest {

    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepository productRepository;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    OptionRepository optionRepository;

    @Test
    void 상품_추가() {
        //given
        Category category = new Category(1L, "테스트카테고리");
        Product product = new Product(1L, "테스트상품", 1500, "테스트주소", category);
        given(categoryRepository.findByName("테스트카테고리")).willReturn(Optional.of(category));

        //when
        productService.insertProduct(new ClientProductDto(product.getName(), product.getPrice(), product.getImageUrl(), product.getCategory().getName()));

        //then
        then(productRepository).should().save(any());
        then(optionRepository).should().save(any());
    }

    @Test
    void 상품_조회() {
        //given
        Category category = new Category(1L, "테스트카테고리");
        Product product = new Product(1L, "테스트상품", 1500, "테스트주소", category);
        given(productRepository.findById(1L)).willReturn(Optional.of(product));

        //when
        productService.getProduct(1L);

        //then
        then(productRepository).should().findById(1L);
    }

    @Test
    void 상품_전체_조회() {
        //given
        given(productRepository.findAll()).willReturn(Collections.emptyList());

        //when
        productService.getProductAll();

        //then
        then(productRepository).should().findAll();
    }

    @Test
    void 상품_전체_조회_페이지() {
        //given
        int PAGE = 1;
        int SIZE = 4;
        String SORT = "name";
        String DIRECTION = "desc";
        Pageable pageable = PageRequest.of(PAGE, SIZE, Sort.Direction.fromString(DIRECTION), SORT);

        //when
        productService.getProductAll(pageable);

        //then
        then(productRepository).should().findAll(pageable);
    }

    @Test
    void 상품_수정() {
        //given
        Category category = new Category(1L, "테스트카테고리");
        Product product = new Product(1L, "테스트상품", 1500, "테스트주소", category);
        given(productRepository.findById(1L)).willReturn(Optional.of(product));
        given(categoryRepository.findByName("테스트카테고리")).willReturn(Optional.of(category));

        //when
        ProductDto updatedProductDto = new ClientProductDto("테스트상품수정", 2000, "테스트주소수정", "테스트카테고리");
        productService.updateProduct(1L, updatedProductDto);

        //then
        then(productRepository).should().save(any());
    }

    @Test
    void 상품_삭제() {
        //given
        Category category = new Category(1L, "테스트카테고리");
        Product product = new Product(1L, "테스트상품", 1500, "테스트주소", category);
        given(productRepository.findById(1L)).willReturn(Optional.of(product));

        //when
        productService.deleteProduct(1L);

        //then
        then(productRepository).should().deleteById(1L);
    }

    @Test
    void 존재하지_않는_상품_조회() {
        //given
        given(productRepository.findById(any())).willReturn(Optional.empty());

        //when, then
        assertThatThrownBy(() -> productService.getProduct(-1L)).isInstanceOf(
            NoSuchElementException.class);
    }
}
