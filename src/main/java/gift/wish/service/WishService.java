package gift.wish.service;

import gift.exception.product.ProductNotFoundException;
import gift.exception.user.UserNotFoundException;
import gift.exception.wish.WishNotFoundException;
import gift.product.entity.Product;
import gift.product.repository.ProductRepository;
import gift.user.entity.User;
import gift.user.repository.UserRepository;
import gift.util.mapper.WishMapper;
import gift.wish.dto.request.AddWishRequest;
import gift.wish.dto.request.UpdateWishRequest;
import gift.wish.dto.response.WishResponse;
import gift.wish.entity.Wish;
import gift.wish.repository.WishRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishService {

    private final WishRepository wishRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public WishService(WishRepository wishRepository, ProductRepository productRepository,
        UserRepository userRepository) {
        this.wishRepository = wishRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public Page<WishResponse> getWishes(Long userId, Pageable pageable) {
        Page<Wish> wishes = wishRepository.findByUserId(userId, pageable);
        validateWishPage(wishes);
        return wishes.map(WishMapper::toResponse);
    }

    @Transactional
    public Long addWish(Long userId, AddWishRequest request) {
        Product product = productRepository.findById(request.productId())
            .orElseThrow(ProductNotFoundException::new);
        User user = userRepository.findById(userId)
            .orElseThrow(UserNotFoundException::new);

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
        wish.changeQuantity(request);
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

    private void validateWishPage(Page<Wish> wishes) {
        if (wishes == null || wishes.isEmpty()) {
            throw new WishNotFoundException("위시리스트가 존재하지 않습니다.");
        }
    }

}
