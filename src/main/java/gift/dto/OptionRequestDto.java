package gift.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class OptionRequestDto {

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9가-힣\\(\\)\\[\\]\\+\\-\\&\\/\\_]*$",message = "허용되지 않는 문자가 있습니다.")
    private String name;

    @NotNull
    @Min(1)
    @Max(100000000)
    private int quantity;

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

}
