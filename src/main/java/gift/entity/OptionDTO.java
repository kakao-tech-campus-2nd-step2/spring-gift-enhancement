package gift.entity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class OptionDTO {
    @Size(min = 1, max = 50)
    @Pattern(regexp = "^[a-zA-Z가-힣()\\[\\]\\+\\-&/_\s]+$")
    private String name;
    @Min(1)
    @Max(99_999_999) // 100,000,000 미만
    private int quantity;

    public OptionDTO() {
    }

    public OptionDTO(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "OptionDTO{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
