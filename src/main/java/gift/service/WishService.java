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

    public List<WishResponseDto> getAll(String tokenValue) {
        Long memberId = translateIdFrom(tokenValue);
        List<Wish> wishes = wishRepository.findAllByMember_id(memberId);
        List<WishResponseDto> wishResponseDtos = wishes.stream().map(this::fromEntity).toList();
        return wishResponseDtos;
    }

    public WishResponseDto fromEntity(Wish wish) {
        String token = makeTokenFrom(wish.getMemberId());
        return new WishResponseDto(wish.getProductId(), token);
    }

    private String makeTokenFrom(Long userId) {
        return Base64.getEncoder().encodeToString(userId.toString().getBytes());
    }

    public boolean delete(Long id, String token) throws IllegalAccessException {

        Long userId = translateIdFrom(token);
        Wish candidateWish = wishRepository.findById(id).get();
        Long wishUserId = candidateWish.getMemberId();

        if (userId.equals(wishUserId)) {
            wishRepository.delete(candidateWish);
            return true;
        }
        return false;
    }

    private Long translateIdFrom(String tokenValue) {
        return tokenService.getUserIdByDecodeTokenValue(tokenValue);
    }

    public Page<WishResponseDto> getWishes(Pageable pageable) {
        return wishRepository.findAll(pageable).map(this::fromEntity);
    }
}
