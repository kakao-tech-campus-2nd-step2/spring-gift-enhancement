package gift.dto.requestDto;

import gift.domain.Option;
import gift.domain.Product;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record OptionNameUpdateRequestDTO(
    @NotNull
    @Size(min = 1, max = 50)
    @Pattern(regexp = "^[\\w가-힣ㄱ-ㅎㅏ-ㅣ()\\[\\]+\\-&/_]*$", message = "( ), [ ], +, -, &, /, _ 외 특수문자는 사용할 수 없습니다.")
    String name) {

    public Option toEntity(int quantity, Product product){
        return new Option(name, quantity, product);
    }
}
