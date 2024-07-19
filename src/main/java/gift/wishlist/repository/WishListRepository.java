package gift.wishlist.repository;

import gift.wishlist.entity.UserProduct;
import gift.wishlist.entity.WishList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishList, Long> {
    boolean existsByUserProduct(UserProduct userProduct);
    Page<WishList> findByUserProductUserId(Long userId, Pageable pageable);
}
