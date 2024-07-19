package gift.domain;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.List;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50, unique = true)
    private String name;
    @Column(nullable = false)
    private int quantity;

    @ManyToOne(fetch = LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    Product product;

    protected Option() {
    }

    public Option(String name, int quantity, Product product) {
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

    public void updateName(String name) {
        this.name = name;
    }

    public static class Options {
        List<Option> optionList;

        public Options(List<Option> optionList) {
            this.optionList = optionList;
        }

        public List<Option> getOptionList() {
            return optionList;
        }

        public void validDuplicateName(String name) {
            if (optionList.stream().anyMatch(it -> it.name.equals(name))){
                throw new IllegalArgumentException("같은 이름의 옵션이 존재합니다.");
            }
        }

        public void validOptionCount() {
            if(optionList.isEmpty()){
                throw new IllegalStateException("상품에는 최소 한 개 이상의 옵션이 존재해야합니다.");
            }
        }
    }
}
