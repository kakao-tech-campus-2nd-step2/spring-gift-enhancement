package gift.domain.option;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class OptionDTO {

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9 ()\\[\\]+\\-&/_ㄱ-ㅎㅏ-ㅣ가-힣]*$", message = "특수문자는 ( ), [ ], +, -, &, /, _ 만 사용 가능합니다.")
    private String name;
    @NotNull
    @Min(value = 0, message = "옵션 수량은 최소 1개입니다.")
    @Max(value = 999999999L, message = "옵션 수량은 최대 1억개 미만입니다.")
    private Long quantity;

    public OptionDTO(String name, Long quantity) {
        this.name = name;
        this.quantity = quantity;
    }
}
