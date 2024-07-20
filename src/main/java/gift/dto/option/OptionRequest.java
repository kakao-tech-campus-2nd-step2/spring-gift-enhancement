package gift.dto.option;

import gift.model.option.Option;
import jakarta.validation.constraints.*;

public class OptionRequest {

    @Size(max = 50, message = "옵션의 이름은 최대 50자까지 입력할 수 있습니다.")
    @Pattern(regexp = "[\\s\\(\\)\\[\\]\\+\\-&/_a-zA-Z0-9\uAC00-\uD7AF]*", message = "특수문자 오류")
    @NotBlank
    private String name;

    @Max(value = 99999999, message = "옵션 수량은 1억 미만의 수만 입력가능합니다.")
    @Min(value = 1, message = "옵션 수량은 1 이상의 수만 입력가능합니다.")
    @NotNull
    private int quantity;

    public OptionRequest(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
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

    public Option toEntity() {
        return new Option(this.name, this.quantity);
    }
}
