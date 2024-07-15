package gift.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name="wishes")
public class Wish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    protected Wish () {
    }

    private Wish(Builder builder) {
        this.id = builder.id;
        this.member = builder.member;
        this.product = builder.product;
        this.quantity = builder.quantity;
        if(builder.member != null && builder.id == null){
            this.member.addWish(this);
        }
        if(builder.product != null && builder.id == null){
            this.product.addWish(this);
        }
    }

    public Wish(Long id, Member member, Product product, int quantity) {
        this.id = id;
        this.member = member;
        this.product = product;
        this.quantity = quantity;
    }

    public Wish(Member member, Product product, int quantity) {
        this.member = member;
        this.product = product;
        this.quantity = quantity;
    }

    public void updateQuantity(int quantity){
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public static class Builder{
        private Long id;
        private Member member;
        private Product product;
        private int quantity;

        public Builder id(Long id){
            this.id = id;
            return this;
        }

        public Builder member(Member member){
            this.member = member;
            return this;
        }

        public Builder product(Product product){
            this.product = product;
            return this;
        }

        public Builder qunatity(int quantity){
            this.quantity = quantity;
            return this;
        }

        public Wish build() {
            return new Wish(this);
        }
    }

    public void detachFromMemberAndProduct() {
        if(this.product!=null){
            this.product.removeWish(this);
        }
        if(member!=null){
            this.member.removeWish(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wish wish = (Wish) o;
        return Objects.equals(id, wish.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}