package gift.option;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class OptionRequest {
    private Long optionId;

    @Size(min = 1, max = 50)
    @Pattern(regexp = "^[A-Za-z가-힣0-9()\\[\\]\\-&/_+\\s]*$", message = "( ), [ ], +, -, &, /, _ 를 제외한 특수문자는 사용할 수 없습니다.")
    @NotNull
    private String name;

    @NotNull
    @Min(1)
    @Max(1_000_000_000)
    private int quantity;

    @NotNull
    private Long productId;

    public Long getOptionId(){
        return optionId;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public Option toEntity(){
        return new Option(this.optionId, this.name,this.quantity,this.productId);
    }
}
