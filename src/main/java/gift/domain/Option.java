package gift.domain;

import gift.dto.request.OptionRequest;
import gift.util.ProductValidationUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static gift.constant.Message.*;

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

    public Option(OptionRequest optionRequest) {
        ProductValidationUtil.isValidOptionQuantity(optionRequest.getQuantity());
        this.name = optionRequest.getName();
        this.quantity = optionRequest.getQuantity();
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

