package gift.product.infra;

import gift.product.domain.WishList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class WishLIstRepository {
    private final WishListJpaRepository wishListJpaRepository;

    public WishLIstRepository(WishListJpaRepository wishListJpaRepository) {
        this.wishListJpaRepository = wishListJpaRepository;
    }

    public WishList findByUserId(Long userId) {
        return wishListJpaRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 위시리스트가 존재하지 않습니다."));
    }

    public Page<WishList> findByUserId(Long userId, Pageable pageable) {
        return wishListJpaRepository.findByUserId(userId, pageable);
    }

    public void save(WishList wishList) {
        wishListJpaRepository.save(wishList);
    }
}
