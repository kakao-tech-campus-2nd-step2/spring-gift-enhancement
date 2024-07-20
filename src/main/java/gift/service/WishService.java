package gift.service;

import gift.dto.WishResponseDto;
import gift.entity.Product;
import gift.entity.Member;
import gift.entity.Wish;
import gift.repository.ProductRepositoryInterface;
import gift.repository.MemberRepositoryInterface;
import gift.repository.WishRepositoryInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {
    private final WishRepositoryInterface wishRepositoryInterface;
    private final ProductRepositoryInterface productRepositoryInterface;
    private final MemberRepositoryInterface memberRepositoryInterface;
    private final TokenService tokenService;

    public WishService(WishRepositoryInterface wishRepositoryInterface,
                       ProductRepositoryInterface productRepositoryInterface,
                       MemberRepositoryInterface memberRepositoryInterface,
                       TokenService tokenService) {

        this.wishRepositoryInterface = wishRepositoryInterface;
        this.productRepositoryInterface = productRepositoryInterface;
        this.memberRepositoryInterface = memberRepositoryInterface;
        this.tokenService = tokenService;

    }

    public WishResponseDto save(Long productId, String tokenValue) {

        Long userId = translateIdFrom(tokenValue);
        Member member = memberRepositoryInterface.findById(userId).get();
        Product product = productRepositoryInterface.findById(productId).get();
        Wish newWish = new Wish(product, member);

        return WishResponseDto.fromEntity(wishRepositoryInterface.save(newWish));
    }

    public List<WishResponseDto> getAll(String tokenValue) {

        Long userId = translateIdFrom(tokenValue);
        List<Wish> wishes = wishRepositoryInterface.findAllByUserId(userId);

        List<WishResponseDto> wishDtos = wishes.stream().map(WishResponseDto::fromEntity).toList();
        return wishDtos;
    }

    public void delete(Long id, String token) throws IllegalAccessException {

        Long userId = translateIdFrom(token);
        Wish candidateWish = wishRepositoryInterface.findById(id).get();
        Long wishUserId = candidateWish.getUserId();

        if (userId.equals(wishUserId)) {
            wishRepositoryInterface.delete(candidateWish);
        }
    }

    private Long translateIdFrom(String tokenValue) {
        return tokenService.getUserIdByDecodeTokenValue(tokenValue);
    }

    public Page<WishResponseDto> getWishes(Pageable pageable) {
        return wishRepositoryInterface.findAll(pageable).map(WishResponseDto::fromEntity);
    }
}