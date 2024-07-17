package gift.init;

import gift.domain.Wish.createWish;
import gift.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WishCreator {

    @Autowired
    private WishService wishService;

    public void WishCreator() {
        wishService.createWish(1L, new createWish(1L));
        wishService.createWish(1L, new createWish(2L));
        wishService.createWish(2L, new createWish(3L));
        wishService.createWish(2L, new createWish(1L));
    }
}
