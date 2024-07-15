package gift.product.validation;

import gift.product.exception.InvalidIdException;
import gift.product.exception.UnauthorizedException;
import gift.product.model.Member;
import gift.product.model.Product;
import gift.product.model.Wish;
import gift.product.repository.ProductRepository;
import gift.product.repository.WishListRepository;
import gift.product.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class WishListValidation {

    public static final String NOT_EXIST_ID = "요청한 id가 위시리스트에 존재하지 않습니다.";
    public static final String NO_PERMISSION = "본인의 위시 리스트만 수정할 수 있습니다.";
    public static final String INVALID_TOKEN = "유효하지 않은 토큰입니다.";

    private final WishListRepository wishListRepository;
    private final ProductRepository productRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public WishListValidation(
            WishListRepository wishListRepository,
            ProductRepository productRepository,
            JwtUtil jwtUtil
    ) {
        this.wishListRepository = wishListRepository;
        this.productRepository = productRepository;
        this.jwtUtil = jwtUtil;
    }

    public Wish registerValidation(String authorization, Long productId) {

        Member member = jwtUtil.identification(authorization);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new InvalidIdException(NOT_EXIST_ID));

        return new Wish(member, product);
    }

    public void deleteValidation(String authorization, Long id) {
        System.out.println("[WishListValidation] deleteValidation()");

        Member member = jwtUtil.identification(authorization);

        Wish wish = wishListRepository.findById(id)
                .orElseThrow(() -> new InvalidIdException(NOT_EXIST_ID));

        if(!Objects.equals(wish.getMember().getId(), member.getId()))
            throw new UnauthorizedException(NO_PERMISSION);
    }
}
