package gift.dto.request;


import jakarta.validation.constraints.*;

public class OptionRequest {

    @NotBlank(message = "옵션 이름을 입력하세요.")
    @Pattern(regexp = "^[\\w\\s()\\[\\]+\\-&/]+$")
    private String name;

    @Min(1)
    @Max(99999999)
    @NotNull(message = "옵션 수량을 입력하세요.")
    private Integer quantity;
}
