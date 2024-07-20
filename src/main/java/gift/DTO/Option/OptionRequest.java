package gift.DTO.Option;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class OptionRequest {
    @Size(max=50, message = "옵션 이름은 공백을 포함하여 최대 50자까지 입력할 수 있습니다")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9\\s\\(\\)\\[\\]\\+\\-\\&\\/\\_]+$",
            message = "특수문자는 ( ), [ ], +, -, &, /, _ 만 허용되며, 한글, 영어, 숫자만 입력 가능합니다.")
    String name;
    @Size(min=1, max=99999999, message = "옵션 수량은 최소 1개 이상 1억 개 미만이어야 합니다")
    Long quantity;

    public OptionRequest(String name, Long quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
