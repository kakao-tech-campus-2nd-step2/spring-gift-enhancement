package gift.product.validation;

import gift.product.exception.InvalidIdException;
import gift.product.exception.UnauthorizedException;
import gift.product.model.Member;
import gift.product.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class WishListValidation {

    public static final String NOT_EXIST_ID = "요청한 id가 위시리스트에 존재하지 않습니다.";
    public static final String NO_PERMISSION = "본인의 위시 리스트만 수정할 수 있습니다.";

    private final WishListRepository wishListRepository;

    @Autowired
    public WishListValidation(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    public void deleteValidation(Long id, Member member) {
        System.out.println("[WishListValidation] deleteValidation()");
        if(wishListRepository.findById(id).isPresent())
            throw new InvalidIdException(NOT_EXIST_ID);
        if(!Objects.equals(wishListRepository.findById(id).get().getMember().getId(), member.getId()))
            throw new UnauthorizedException(NO_PERMISSION);
    }

}
