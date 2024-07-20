package gift.product.presentation.dto;

import gift.product.business.dto.OptionIn;
import gift.product.business.dto.OptionRegisterDto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class OptionRequest {

    public record Create(
        @Size(max = 50, message = "옵션 이름은 공백을 포함한 50자 이하여야 합니다.")
        @Pattern(
            regexp = "^[a-zA-Z0-9ㄱ-ㅎ가-힣 ()\\[\\]+\\-&/_]*$",
            message = "오직 문자, 공백 그리고 특수문자 (),[],+,&,-,/,_만 허용됩니다."
        )
        String name,
        @Min(value = 1, message = "수량은 1개 이상이어야 합니다.")
        @Max(value = 100000000, message = "수량은 100,000,000개 이하이어야 합니다.")
        Integer quantity
    ) {
        public OptionIn.Create toOptionInCreate() {
            return new OptionIn.Create(name, quantity);
        }
    }

}
