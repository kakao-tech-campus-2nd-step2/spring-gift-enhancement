package gift.option;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class OptionRequest {
    @NotBlank(message = "이름은 공백으로 둘 수 없습니다.")
    @Pattern(regexp = "^[a-zA-Zㄱ-ㅎ가-힣0-9\\(\\)\\[\\]\\+\\-&/\\_ ]{1,51}$", message = "이름은 공백을 포함하여 최대 50자까지 입력할 수 있습니다. 특수문자는 ( ) [ ] + - & / _ 만 사용 가능합니다.")
    String name;
    @NotNull(message = "수량은 공백으로 둘 수 없습니다.")
    @Size(min = 1, max = 100000000, message = "최소 1개 이상 1억개 미만의 수량이 있어야 합니다.")
    Long quantity;

    public OptionRequest() {
    }

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
