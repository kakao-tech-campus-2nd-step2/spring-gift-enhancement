package gift.Model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RequestOption (
        @Pattern(
                regexp = "^[a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣 ()\\[\\]+\\-\\&/_]*$",
                message = "옵션 이름에는 허용된 특수 문자만 포함될 수 있습니다: (), [], +, -, &, /, _"
        )
        @Size(max = 50,
                message = "옵션 이름의 길이는 공백을 포함하여 최대 50자 입니다")
        @NotNull (message =  "옵션 이름은 필수입니다")
        String name,

        @Size(min=1 , max = 99999999, message = "옵션 수량은 최소 1개 이상 최대 1억개 미만입니다")
        int quantity
){
}
