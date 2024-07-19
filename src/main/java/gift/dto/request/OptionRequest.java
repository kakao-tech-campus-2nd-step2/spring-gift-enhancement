package gift.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static gift.constant.Message.*;

public class OptionRequest {

    @NotBlank(message = REQUIRED_FIELD_MSG)
    @Size(max = 50, message = LENGTH_ERROR_MSG)
    @Pattern(regexp = "^[a-zA-Z0-9가-힣 ()\\[\\]+\\-&/_]*$", message = SPECIAL_CHAR_ERROR_MSG)
    private String name;

    @NotNull(message = REQUIRED_FIELD_MSG)
    private int quantity;

    public OptionRequest(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
}
