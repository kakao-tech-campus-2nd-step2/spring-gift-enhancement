package gift.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import gift.domain.category.entity.Category;
import gift.domain.wishlist.dto.WishRequest;
import gift.domain.member.entity.Member;
import gift.domain.product.entity.Product;
import gift.domain.wishlist.dto.WishResponse;
import gift.domain.wishlist.entity.Wish;
import gift.domain.member.repository.MemberRepository;
import gift.domain.product.repository.ProductRepository;
import gift.domain.wishlist.repository.WishRepository;
import gift.domain.wishlist.service.WishService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class WishServiceTest {

    @InjectMocks
    WishService wishService;
    @Mock
    WishRepository wishRepository;
    @Mock
    MemberRepository memberRepository;
    @Mock
    ProductRepository productRepository;

    @Test
    @DisplayName("getWishesByMember 테스트")
    void getWishesByMemberTest() {
        // given
        Member savedMember = new Member(1L, "email@google.co.kr", "password");

        Category savedCategory1 = new Category(1L, "test", "color", "image", "description");
        Category savedCategory2 = new Category(2L, "test", "color", "image", "description");
        Product product1 = new Product("product1", 1000, "product1.jpg", savedCategory1);
        Product product2 = new Product("product2", 2000, "product2.jpg", savedCategory2);

        Wish wish1 = new Wish(savedMember, product1);
        Wish wish2 = new Wish(savedMember, product2);
        List<Wish> wishList = Arrays.asList(wish1, wish2);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Wish> wishPage = new PageImpl<>(wishList, pageable, wishList.size());

        doReturn(wishPage).when(wishRepository).findAllByMember(savedMember, pageable);

        Page<WishResponse> expected = wishPage.map(this::entityToDto);

        // when
        Page<WishResponse> actual = wishService.getWishesByMember(savedMember,
            pageable.getPageNumber(),
            pageable.getPageSize());

        // then
        assertAll(
            () -> assertThat(actual).isNotNull(),
            () -> IntStream.range(0, actual.getContent().size()).forEach(i -> {
                assertThat(actual.getContent().get(i).getMemberId())
                    .isEqualTo(expected.getContent().get(i).getMemberId());
                assertThat(actual.getContent().get(i).getProductId())
                    .isEqualTo(expected.getContent().get(i).getProductId());
            })
        );
    }

    @Test
    @DisplayName("위시 리스트 추가 테스트")
    void createWishTest() {
        // given
        WishRequest wishRequest = new WishRequest(1L, 1L);

        Member savedMember = new Member(1L, "email@google.com", "password");
        Category savedCategory = new Category(1L, "test", "color", "image", "description");
        Product savedProduct = new Product(1L, "test", 1000, "test.jpg", savedCategory);

        Wish saveWish = new Wish(savedMember, savedProduct);
        WishResponse expected = entityToDto(saveWish);

        doReturn(Optional.of(savedMember)).when(memberRepository)
            .findById(wishRequest.getMemberId());
        doReturn(Optional.of(savedProduct)).when(productRepository)
            .findById(wishRequest.getProductId());
        doReturn(saveWish).when(wishRepository).save(any(Wish.class));

        // when
        WishResponse actual = wishService.createWish(wishRequest);

        // then
        assertThat(actual.getMemberId()).isEqualTo(expected.getMemberId());
        assertThat(actual.getProductId()).isEqualTo(expected.getProductId());

    }

    @Test
    @DisplayName("위시 리시트 삭제 테스트")
    void deleteWishTest() {
        Long id = 1L;
        Member savedMember = new Member(1L, "email@google.co.kr", "password");
        Category savedCategory = new Category(1L, "test", "color", "image", "description");
        Product savedProduct = new Product(1L, "test", 1000, "test.jpg", savedCategory);

        Wish wish = new Wish(savedMember, savedProduct);

        doReturn(Optional.of(wish)).when(wishRepository).findById(id);

        wishService.deleteWish(id, savedMember);

        verify(wishRepository, times(1)).delete(wish);

    }

    private WishResponse entityToDto(Wish wish) {
        return new WishResponse(wish.getId(), wish.getMember().getId(),
            wish.getProduct().getId());
    }
}