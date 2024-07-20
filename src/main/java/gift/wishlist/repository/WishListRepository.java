package gift.wishlist.repository;

import gift.member.model.Member;
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
    Page<WishList> findById(Long id);

    WishList findByMember(Member member);

    Optional<WishList> findByMemberAndProduct(Member member, Product product);

    Page<WishList> findByMember(Member member, Pageable pageable);

    @Modifying
    @Query("update WishList w set w.product = :product where w.member.id = :member_id")
    void addProductToWishList(@Param("member_id") Long memberId, @Param("product") Product product);

    @Modifying
    @Query("update WishList w set w.product.id = :product_id where w.member.id = :member_id")
    void removeProductFromWishList(@Param("member_id") Long memberId, @Param("product_id") Long productId);
}