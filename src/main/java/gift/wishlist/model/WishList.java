package gift.wishlist.model;

import gift.member.model.Member;
import gift.product.model.Product;
import jakarta.persistence.*;

@Entity
@Table(name = "wishlists")
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** fetch = FetchType.LAZY를 사용하여 WishList가 로드될 때 Member와 Product는 즉시 로드되지 않음.
     Member나 Product에 접근할 때 데이터베이스에서 해당 데이터를 로드함. **/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public WishList() {
    }

    public WishList(Member member, Product product) {
        this.member = member;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Product getProduct() {
        return product;
    }
}