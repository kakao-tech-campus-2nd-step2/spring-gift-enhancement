package gift.option.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class OptionCount {
    @Column(name = "count")
    private Long optionCountValue;

    public OptionCount() {
    }

    public OptionCount(Long optionCountValue) {
        this.optionCountValue = optionCountValue;
    }

    public Long getOptionCountValue() {
        return optionCountValue;
    }

    @Override
    @JsonValue
    public String toString() {
        return Long.toString(optionCountValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OptionCount that = (OptionCount) o;
        return Objects.equals(optionCountValue, that.optionCountValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(optionCountValue);
    }
}
