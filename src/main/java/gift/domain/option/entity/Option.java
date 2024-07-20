package gift.domain.option.entity;

import gift.domain.option.exception.OptionNameDuplicateException;
import gift.domain.option.exception.OptionNameLengthException;
import gift.domain.option.exception.OptionQuantityRangeException;
import gift.domain.product.entity.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.List;

@Entity
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    protected Option() {
    }

    public Option(String name, int quantity) {
        this(null, name, quantity, null);
    }

    public Option(String name, int quantity, Product product) {
        this(null, name, quantity, product);
    }

    public Option(Long id, String name, int quantity, Product product) {
        if(quantity < 1 || quantity >= 100_000_000){
            throw new OptionQuantityRangeException("수량은 1개 이상 1억개 미만으로 설정해주세요");
        }
        if(name.length() > 50){
            throw new OptionNameLengthException("옵션 이름은 50자 이하로 설정해주세요.");
        }
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void checkDuplicateName(List<Option> optionList) {

        if (optionList.stream().map(Option::getName).anyMatch((name) -> name.equals(this.name))) {
            throw new OptionNameDuplicateException(this.name + "은 중복된 이름입니다.");
        }
    }

    public void addProduct(Product product) {
        this.product = product;
    }

    public void subtract(int quantity) {
        this.quantity -= quantity;
    }
}
