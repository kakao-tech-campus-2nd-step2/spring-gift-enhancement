package gift.model.option;

import gift.model.product.Product;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record OptionRequest(
    @NotBlank(message = "이름은 필수 입력값입니다.")
    @Size(max = 15, message = "이름의 최대 글자수는 15입니다.")
    @Pattern(
        regexp = "^[가-힣a-zA-Z0-9\\(\\)\\[\\]\\+\\-\\&\\/\\_\\s]*$",
        message = "상품 이름은 최대 15자, 한글과 영문, 그리고 특수기호([],(),+,-,&,/,_)만 사용 가능합니다!"
    )
    String name,
    @Min(value = 1, message = "옵션의 수량은 최소 1개 이상입니다.")
    @Max(value = 9999999, message = "옵션의 최대 수량은 1억개 미만입니다.")
    int quantity
){

}
