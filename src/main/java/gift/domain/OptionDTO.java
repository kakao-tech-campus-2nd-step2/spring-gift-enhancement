package gift.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class OptionDTO {
    private Long id;

    @NotNull
    @Size(min = 1, max = 50, message = "옵션의 이름은 최소 1자부터 최대 50자 미만입니다.")
    private String name;

    @Min(value = 1, message = "수량은 최소 1개 이상, 최대 1억개 미만입니다.")
    @Max(value = 100_000_000, message = "수량은 최소 1개 이상, 최대 1억개 미만입니다.")
    private int quantity;

    public OptionDTO() {}

    public OptionDTO(Long id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}