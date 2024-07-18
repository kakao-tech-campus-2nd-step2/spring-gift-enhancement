package gift.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

@Embeddable
public class OptionName {

    @NotNull(message = "이름을 입력해주세요.")
    @Length(min = 1, max = 50, message = "1자 ~ 50자까지 가능합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣\\(\\)\\[\\]\\+\\-\\&\\/\\_ ]*$", message = "사용불가한 특수 문자가 포함되어 있습니다.")
    private String name;

    protected OptionName() {
    }

    public OptionName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OptionName name1 = (OptionName) o;
        return name.equals(name1.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}