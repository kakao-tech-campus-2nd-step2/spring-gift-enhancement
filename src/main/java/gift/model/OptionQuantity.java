package gift.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Embeddable
public class OptionQuantity {

    @Min(value = 1, message = "옵션 수량은 최소 1개 이상이어야 합니다.")
    @Max(value = 99999999, message = "옵션 수량은 1억 개 미만이어야 합니다.")
    private int quantity;

    protected OptionQuantity() {
    }

    public OptionQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

}