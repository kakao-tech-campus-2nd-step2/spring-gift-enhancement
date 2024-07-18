package gift.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class OptionNameDTO {

    @NotNull(message = "이름을 입력해주세요.")
    @NotBlank(message = "이름은 공백일 수 없습니다.")
    @Length(min = 1, max = 50, message = "1자 ~ 50자까지 가능합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣\\(\\)\\[\\]\\+\\-\\&\\/\\_]*$", message = "사용불가한 특수 문자가 포함되어 있습니다.")
    private String name;

    public OptionNameDTO() {}

    public OptionNameDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}