package gift.doamin.product.dto;

import gift.doamin.product.entity.Option;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class OptionForm {

    @Pattern(regexp = "^[ㄱ-ㅎㅏ-ㅣ가-힣\\w ()\\[\\]+\\-&/_]{1,50}$", message = "이름 형식이 잘못되었습니다.")
    private String name;

    @Positive
    @Max(99_999_999)
    private Integer quantity;

    public OptionForm(String name, Integer quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Option toEntity() {
        return new Option(name, quantity);
    }
}
