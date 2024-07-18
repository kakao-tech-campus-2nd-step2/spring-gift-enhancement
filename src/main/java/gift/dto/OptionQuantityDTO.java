package gift.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class OptionQuantityDTO {

    @Min(value = 1, message = "옵션 수량은 최소 1개 이상이어야 합니다.")
    @Max(value = 99999999, message = "옵션 수량은 1억 개 미만이어야 합니다.")
    private int quantity;

    public OptionQuantityDTO() {}

    public OptionQuantityDTO(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}