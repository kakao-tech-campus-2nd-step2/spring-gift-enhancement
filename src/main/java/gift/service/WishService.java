package gift.service;

import gift.dto.WishResponseDto;
import gift.entity.Member;
import gift.entity.Product;
import gift.entity.Wish;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
public class WishService {
    private final WishRepository wishRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final TokenService tokenService;

    public WishService(WishRepository wishRepository,
                       ProductRepository productRepository,
                       MemberRepository memberRepository,
                       TokenService tokenService) {

        this.wishRepository = wishRepository;
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
        this.tokenService = tokenService;

    }

    public WishResponseDto save(Long productId, String tokenValue) {

        Long userId = translateIdFrom(tokenValue);
        Member member = memberRepository.findById(userId).get();
        Product product = productRepository.findById(productId).get();
        Wish newWish = new Wish(product, member);

        return fromEntity(wishRepository.save(newWish));
    }

    public List<Wish> getAll(String tokenValue) {
        Long userId = translateIdFrom(tokenValue);
        List<Wish> wishes = wishRepository.findAllByUserId(userId);
        return wishes;
    }

    public WishResponseDto fromEntity(Wish wish) {
        String token = makeTokenFrom(wish.getMemberId());
        return new WishResponseDto(wish.getProductId(), token);
    }

    private String makeTokenFrom(Long userId) {
        return Base64.getEncoder().encodeToString(userId.toString().getBytes());
    }


    public WishResponseDto getAllAndMakeWishResponseDto(String tokenValue) {
        WishResponseDto wishResponseDto = new WishResponseDto(getAll(tokenValue));
        return wishResponseDto;
    }


    public WishResponseDto delete(Long id, String token) throws IllegalAccessException {

        Long userId = translateIdFrom(token);
        Wish candidateWish = wishRepository.findById(id).get();
        Long wishUserId = candidateWish.getMemberId();
        WishResponseDto wishResponseDto = new WishResponseDto(candidateWish);

        if (userId.equals(wishUserId)) {
            wishRepository.delete(candidateWish);
            wishResponseDto.setHttpStatus(HttpStatus.OK);
            return wishResponseDto;
        }
        wishResponseDto.setHttpStatus(HttpStatus.BAD_REQUEST);
        return wishResponseDto;
    }

    private Long translateIdFrom(String tokenValue) {
        return tokenService.getUserIdByDecodeTokenValue(tokenValue);
    }

    public Page<WishResponseDto> getWishes(Pageable pageable) {
        return wishRepository.findAll(pageable).map(this::fromEntity);
    }
}