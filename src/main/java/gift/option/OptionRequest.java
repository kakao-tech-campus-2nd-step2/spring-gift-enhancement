package gift.option;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class OptionRequest {

    @NotBlank
    @Size(max = 50, message = "옵션 이름은 공백을 포함하여 최대 50자입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9기-힣 ()\\[\\]+\\-&/_]*$")
    private String name;
    @NotNull
    @Max(value = 99_999_999, message = "옵션 수량은 1억개 미만입니다.")
    @Min(value = 1, message = "옵션 수량은 최소 1개 이상입니다.")
    private Integer quantity;

    public OptionRequest() {
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
