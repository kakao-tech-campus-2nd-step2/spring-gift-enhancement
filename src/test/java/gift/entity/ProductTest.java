package gift.entity;

import static org.assertj.core.api.Assertions.assertThat;

import gift.dto.ProductDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

    private final Member member = new Member("sgoh@sgoh", "password");
    private final Category category = new Category("생일선물", "Red", "http", "생일선물 카테고리");
    private final Product product = new Product("kakao", 5000L, "https", category);

    @Test
    @DisplayName("상품 정보 수정 테스트")
    void updateProduct() {
        ProductDto productDto = new ProductDto(null, "카카오 인형", 35000L, "https", 1L, "생일선물");
        product.updateProduct(productDto, category);

        assertThat(product.getName()).isEqualTo(productDto.getName());
        assertThat(product.getPrice()).isEqualTo(productDto.getPrice());
    }

    @Test
    @DisplayName("상품의 wishlist 리스트에 위시리스트 추가 테스트")
    void addWishlist() {
        Wishlist wishlist = new Wishlist(member, product);
        product.addWishlist(wishlist);

        assertThat(product.getWishlist().size()).isEqualTo(1);
    }
}