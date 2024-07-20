package gift.service;

import gift.dto.ProductRequest;
import gift.exception.InvalidProductNameWithKAKAOException;
import gift.model.MemberRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ProductServiceTest {

    @Autowired
    private ProductService productService;
    @Autowired
    private OptionService optionService;

    @Test
    @DisplayName("정상 상품 추가하기")
    void addProductSuccess() {
        //given
        var productRequest = new ProductRequest("상품1", 10000, "이미지 주소", 1L);
        //when
        var savedProduct = productService.addProduct(productRequest, MemberRole.MEMBER);
        //then
        Assertions.assertThat(savedProduct.name()).isEqualTo("상품1");

        productService.deleteProduct(savedProduct.id());
    }

    @Test
    @DisplayName("이용자로 카카오가 포함된 상품 추가하기")
    void addProductFailWithKAKAOName() {
        //given
        var productRequest = new ProductRequest("카카오상품", 10000, "이미지 주소", 1L);
        //when, then
        Assertions.assertThatThrownBy(() -> productService.addProduct(productRequest, MemberRole.MEMBER))
                .isInstanceOf(InvalidProductNameWithKAKAOException.class);
    }

    @Test
    @DisplayName("관리자로 카카오가 포함된 상품 추가하기")
    void addProductSuccessWithKAKAOName() {
        //given
        var productRequest = new ProductRequest("카카오상품", 10000, "이미지 주소", 1L);
        //when
        var savedProduct = productService.addProduct(productRequest, MemberRole.ADMIN);
        //then
        Assertions.assertThat(savedProduct.name()).isEqualTo("카카오상품");

        productService.deleteProduct(savedProduct.id());
    }

    @Test
    @DisplayName("상품 수정하기")
    void updateProduct() {
        //given
        var productRequest = new ProductRequest("상품1", 10000, "이미지 주소", 1L);
        var savedProduct = productService.addProduct(productRequest, MemberRole.MEMBER);
        var id = savedProduct.id();
        var updateDto = new ProductRequest("상품1", 7000, "이미지 주소2", 1L);
        //when
        productService.updateProduct(id, updateDto);
        //then
        var updatedProduct = productService.getProduct(id);
        Assertions.assertThat(updatedProduct.price()).isEqualTo(7000);
        Assertions.assertThat(savedProduct.id()).isEqualTo(updatedProduct.id());

        productService.deleteProduct(id);
    }

    @Test
    @DisplayName("정상 상품 추가시 기본 옵션이 추가되어있다.")
    void addProductSuccessThenHaveDefaultOption() {
        //given
        var productRequest = new ProductRequest("상품1", 10000, "이미지 주소", 1L);
        var savedProduct = productService.addProduct(productRequest, MemberRole.MEMBER);
        //when
        var options = optionService.getOptions(savedProduct.id(), Pageable.unpaged());
        //then
        Assertions.assertThat(options.get(0).name()).isEqualTo("기본");

        productService.deleteProduct(savedProduct.id());
    }
}
