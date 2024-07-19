package gift.main.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record OptionRequest(
        @Size(max = 50, message = "옵션 이름은 공백을 포함하여 최대 50자까지 입력할 수 있다.")
        @Pattern(regexp = "^[a-zA-Z0-9\\s\\(\\)\\[\\]\\+\\-\\&\\/\\_]*$",
                message = "특수 문자는 ( ), [ ], +, -, &, /, _ 만 가능합니다.")
        String name,
        int num) {
}
