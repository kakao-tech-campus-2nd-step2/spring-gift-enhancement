package gift.option.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Embeddable
public class OptionName {

    @NotBlank(message = "옵션을 선택하세요.")
    @Size(max = 50, message = "옵션은 공백 포함 최대 50자")
    @Pattern(regexp = "^[\\w\\s\\(\\)\\[\\]+\\-&/_]*$", message = "옵션명에 잘못된 문자가 있습니다.")
    private String name;

    public OptionName() {}

    public OptionName(String name) {
        this.name = name;
    }

    public String getValue() {
        return name;
    }
}