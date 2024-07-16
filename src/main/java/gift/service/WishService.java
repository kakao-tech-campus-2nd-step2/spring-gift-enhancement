package gift.service;

import gift.dto.wish.request.AddWishRequest;
import gift.dto.wish.request.UpdateWishRequest;
import gift.dto.wish.response.WishResponse;
import gift.entity.Product;
import gift.entity.User;
import gift.entity.Wish;
import gift.exception.wish.WishNotFoundException;
import gift.repository.WishRepository;
import gift.util.mapper.WishMapper;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishService {

    private final WishRepository wishRepository;
    private final ProductService productService;
    private final UserService userService;

    public WishService(WishRepository wishRepository, ProductService productService,
        UserService userService) {
        this.wishRepository = wishRepository;
        this.productService = productService;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    public Page<WishResponse> getWishes(Long userId, Pageable pageable) {
        Page<Wish> wishes = wishRepository.findByUserId(userId, pageable);
        validateWishPage(wishes);
        return wishes.map(WishMapper::toResponse);
    }

    @Transactional
    public Long addWish(Long userId, AddWishRequest request) {
        Product product = productService.getProductById(request.productId());
        User user = userService.getUserById(userId);

        Wish wish = new Wish(user, product, request.quantity());

        Wish savedWish = wishRepository.save(wish);
        return savedWish.getId();
    }

    @Transactional
    public void updateWishes(List<UpdateWishRequest> requests) {
        for (UpdateWishRequest request : requests) {
            updateWish(request);
        }
    }

    @Transactional
    public void deleteWish(Long id) {
        wishRepository.deleteById(id);
    }

    @Transactional
    protected void updateWish(UpdateWishRequest request) {
        Wish wish = getWish(request.id());
        wish.changeQuantity(request.quantity());
        if (wish.isQuantityZero()) {
            wishRepository.delete(wish);
        }
    }

    @Transactional(readOnly = true)
    protected Wish getWish(Long id) {
        return wishRepository.findById(id)
            .orElseThrow(WishNotFoundException::new);
    }

    private void validateWishPage(Page<Wish> wishes) {
        if (wishes == null || wishes.isEmpty()) {
            throw new WishNotFoundException();
        }
    }

}
