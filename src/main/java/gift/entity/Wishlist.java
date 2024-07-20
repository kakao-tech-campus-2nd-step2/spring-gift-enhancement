package gift.entity;

import jakarta.persistence.*;

@Entity
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    private String optionName;

    public Wishlist(){

    }
    public Wishlist(Member member, Product product, String optionName){
        this.member = member;
        this.product = product;
        this.optionName = optionName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getOptionName(){
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }
}
