package gift.option.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class OptionName {
    @Column(name = "name")
    private String optionNameValue;

    public OptionName() {
    }

    public OptionName(String optionNameValue) {
        this.optionNameValue = optionNameValue;
    }

    public String getOptionNameValue() {
        return optionNameValue;
    }

    @Override
    @JsonValue
    public String toString() {
        return optionNameValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OptionName that = (OptionName) o;
        return Objects.equals(optionNameValue, that.optionNameValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(optionNameValue);
    }
}
