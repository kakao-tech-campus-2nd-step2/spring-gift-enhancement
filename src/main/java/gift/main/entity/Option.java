package gift.main.entity;

import gift.main.Exception.CustomException;
import gift.main.Exception.ErrorCode;
import gift.main.dto.OptionRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.Objects;

@Entity
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 20)
    private String optionName;

    @Min(1)
    @Max(100000000)
    private int num;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Option() {

    }

    public Option(String optionName, int num, Product product) {
        this.optionName = optionName;
        this.num = num;
        this.product = product;
    }

    public Option(OptionRequest optionRequest, Product product) {
        this.optionName = optionRequest.name();
        this.num = optionRequest.num();
        this.product = product;
        this.product = product;
    }

    public long getId() {
        return id;
    }

    public String getOptionName() {
        return optionName;
    }

    public int getNum() {
        return num;
    }

    public Product getProduct() {
        return product;
    }

    public void isDuplicate(long optionId, OptionRequest optionRequest) {
        if (this.id == optionId) {
            return;
        }
        if (this.num == optionRequest.num()) {
            throw new CustomException(ErrorCode.ALREADY_EXISTS_OPTION_NUM);
        }
        if (this.optionName == optionRequest.name()) {
            throw new CustomException(ErrorCode.ALREADY_EXISTS_OPTION_NAME);
        }

    }

    public void isDuplicate(OptionRequest optionRequest) {
        if (this.num == optionRequest.num()) {
            throw new CustomException(ErrorCode.ALREADY_EXISTS_OPTION_NUM);
        }
        if (this.optionName == optionRequest.name()) {
            throw new CustomException(ErrorCode.ALREADY_EXISTS_OPTION_NAME);
        }

    }

    public void updateValue(OptionRequest optionRequest) {
        this.optionName = optionRequest.name();
        this.num = optionRequest.num();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Option option = (Option) o;
        return id == option.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
