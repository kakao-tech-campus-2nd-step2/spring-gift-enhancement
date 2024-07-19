package gift.wish;

import gift.common.auth.LoginMemberDto;
import gift.common.exception.ProductException;
import gift.product.ProductErrorCode;
import gift.product.ProductRepository;
import gift.product.model.Product;
import gift.wish.model.Wish;
import gift.wish.model.WishRequestDto;
import gift.wish.model.WishResponseDto;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishService {

    private final WishRepository wishRepository;
    private final ProductRepository productRepository;

    public WishService(WishRepository wishRepository, ProductRepository productRepository) {
        this.wishRepository = wishRepository;
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<WishResponseDto> getWishList(LoginMemberDto loginMemberDto, Pageable pageable) {
        return wishRepository.findAllByMemberId(loginMemberDto.getId(),
                pageable)
            .map(WishResponseDto::from)
            .getContent();
    }

    @Transactional
    public Long addProductToWishList(WishRequestDto wishRequestDto, LoginMemberDto loginMemberDto) throws ProductException {
        Product product = productRepository.findById(wishRequestDto.getProductId())
            .orElseThrow(() -> new ProductException(ProductErrorCode.NOT_FOUND));
        Wish wish = new Wish(loginMemberDto.toEntity(), product, wishRequestDto.getCount());
        wishRepository.save(wish);
        return wish.getId();
    }

    @Transactional
    public void updateProductInWishList(WishRequestDto wishRequestDto,
        LoginMemberDto loginMemberDto) {
        if (wishRequestDto.isCountZero()) {
            wishRepository.deleteByMemberIdAndProductId(loginMemberDto.getId(),
                wishRequestDto.getProductId());
            return;
        }
        Wish wish = wishRepository.findByMemberIdAndProductId(loginMemberDto.getId(),
            wishRequestDto.getProductId()).orElseThrow();
        wish.changeCount(wishRequestDto.getCount());
    }

    @Transactional
    public void deleteProductInWishList(WishRequestDto wishRequestDto,
        LoginMemberDto loginMemberDto) {
        wishRepository.deleteByMemberIdAndProductId(loginMemberDto.getId(),
            wishRequestDto.getProductId());
    }
}
