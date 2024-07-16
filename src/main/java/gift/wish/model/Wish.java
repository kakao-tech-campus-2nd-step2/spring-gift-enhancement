package gift.wish.model;

import gift.member.model.Member;
import gift.product.model.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Entity
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @Column(name = "count", nullable = false)
    private Integer count;
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    protected Wish() {
    }

    public Wish(Integer count) {
        this.count = count;
    }

    public Wish(Member member, Product product, Integer count) {
        this.member = member;
        this.product = product;
        this.count = count;
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

    public Integer getCount() {
        return count;
    }

    public void changeCount(Integer count) {
        this.count = count;
    }
}
