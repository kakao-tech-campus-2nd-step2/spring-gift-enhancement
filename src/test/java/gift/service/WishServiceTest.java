package gift.service;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import gift.category.model.Category;
import gift.common.auth.LoginMemberDto;
import gift.member.model.Member;
import gift.product.ProductRepository;
import gift.product.model.Product;
import gift.wish.WishRepository;
import gift.wish.WishService;
import gift.wish.model.WishRequestDto;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
public class WishServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private WishRepository wishRepository;

    private WishService wishService;

    @BeforeEach
    void setUp() {
        wishService = new WishService(wishRepository, productRepository);
    }

    @Test
    void getWishListTest() {
        given(wishRepository.findAllByMemberId(any(), any())).willReturn(Page.empty());
        LoginMemberDto loginMemberDto = getLoginMemberDto();

        wishService.getWishList(loginMemberDto, PageRequest.of(10, 10));
        then(wishRepository).should().findAllByMemberId(any(), any());
    }

    @Test
    void addProductTest() {
        given(productRepository.findById(any())).willReturn(Optional.of(
            new Product("gamza", 1000, "gamza.jpg", new Category("식품", "##111", "식품.jpg", "식품"))));
        WishRequestDto wishRequestDto = getWishRequestDto();
        LoginMemberDto loginMemberDto = getLoginMemberDto();

        wishService.addProductToWishList(wishRequestDto, loginMemberDto);

        then(wishRepository).should().save(any());
    }

    @Test
    void updateWishTest() {
        //더티 체킹
    }

    @Test
    void deleteWishTest() {
        WishRequestDto wishRequestDto = getWishRequestDto();
        LoginMemberDto loginMemberDto = getLoginMemberDto();

        wishService.deleteProductInWishList(wishRequestDto, loginMemberDto);

        then(wishRepository).should().deleteByMemberIdAndProductId(any(), any());
    }

    private LoginMemberDto getLoginMemberDto() {
        return LoginMemberDto.from(
            new Member(1L, "member1@example.com", "member1", "user"));
    }

    private WishRequestDto getWishRequestDto() {
        WishRequestDto wishRequestDto = new WishRequestDto(1L);
        return wishRequestDto;
    }


}
