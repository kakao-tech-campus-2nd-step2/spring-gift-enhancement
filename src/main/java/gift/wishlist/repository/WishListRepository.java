package gift.wishlist.repository;

import gift.product.model.Product;
import gift.wishlist.model.WishList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishListRepository extends JpaRepository<WishList, String> {
    WishList findByMemberId(Long memberId);

    Optional<WishList> findByMemberIdAndProductId(Long memberId, Long productId);

    Page<WishList> findByMemberId(Long memberId, Pageable pageable);

    @Modifying
    @Query("update WishList w set w.product = :product where w.member.memberId = :member_id")
    void addProductToWishList(@Param("member_id") Long userId, @Param("product") Product product);

    @Modifying
    @Query("update WishList w set w.product.productId = :product_id where w.member.memberId = :member_id")
    void removeProductFromWishList(@Param("member_id") Long member_id, @Param("product_id") Long product_id);
}