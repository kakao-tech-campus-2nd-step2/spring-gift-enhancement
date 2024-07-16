package gift.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;


@Entity
@Table(name = "wishes")
public class Wish extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Products product;

    public Wish() {
    }

    public Wish(Member member, Products product) {
        super();
        this.member = member;
        this.product = product;
    }

    public Member getMember() {
        return member;
    }

    public Products getProduct() {
        return product;
    }

    public static class Builder {
        private Member member;
        private Products product;

        public Builder member(Member member) {
            this.member = member;
            return this;
        }

        public Builder product(Products product) {
            this.product = product;
            return this;
        }

        public Wish build() {
            return new Wish(member, product);
        }
    }
}
