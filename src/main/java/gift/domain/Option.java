package gift.domain;

import gift.dto.request.OptionRequest;
import gift.exception.CustomException;
import gift.util.ProductValidationUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static gift.constant.Message.*;
import static gift.exception.ErrorCode.INVALID_AMOUNT_ERROR;
import static gift.exception.ErrorCode.INVALID_QUANTITY_ERROR;

@Entity
@Table(name = "option")
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = REQUIRED_FIELD_MSG)
    @Size(max = 50, message = LENGTH_ERROR_MSG)
    @Pattern(regexp = "^[a-zA-Z0-9가-힣 ()\\[\\]+\\-&/_]*$", message = SPECIAL_CHAR_ERROR_MSG)
    private String name;
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public Option() { }

    public Option(String name, int quantity, Product product) {
        this.name = name;
        this.quantity = quantity;
        this.product = product;
    }

    public Option(OptionRequest optionRequest) {
        this.name = optionRequest.getName();
        this.quantity = validQuantity(optionRequest.getQuantity());
    }

    public Option(OptionRequest optionRequest, Product product) {
        this.name = optionRequest.getName();
        this.quantity = validQuantity(optionRequest.getQuantity());
        this.product = product;
    }

    public void subtract(int amount) {
        checkAmount(amount);
        this.quantity -= amount;
    }

    private int validQuantity(int quantity) {
        if (quantity < 1 || quantity >= 100_000_000) {
            throw new CustomException(INVALID_QUANTITY_ERROR);
        }
        return quantity;
    }

    private void checkAmount(int amount) {
        if (amount < 1 || amount > this.quantity){
            throw new CustomException(INVALID_AMOUNT_ERROR);
        }
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
}

