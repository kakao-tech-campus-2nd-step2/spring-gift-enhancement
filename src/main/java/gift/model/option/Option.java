package gift.model.option;

import gift.common.exception.AlreadyExistName;
import gift.model.product.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "option")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true) // 동일한 옵션 생성 방지
    private String name;
    private int quantity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public void update(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }
    public Option() {}
    public Option(String name, int quantity, Product product) {
        validationName(name, product);
        this.name = name;
        this.quantity = quantity;
        this.product = product;
    }
    // 옵션명 중복 검증 로직
    private void validationName(String name, Product product) {
        if(product.hasOption(name)) {
            throw new AlreadyExistName("이미 존재하는 옵션명입니다.");
        }
    }
    // 옵션 수량 삭제 메서드
    public Option subtract(Option option, int amount){
        if(option.getQuantity() < amount) {
            throw new IllegalArgumentException("수량은 0보다 작을 수 없습니다.");
        }
        option.quantity = option.getQuantity() - amount;
        return option;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
