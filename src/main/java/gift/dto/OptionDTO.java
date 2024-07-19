package gift.dto;

import gift.domain.Option;
import gift.domain.Product;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class OptionDTO {

    @NotBlank(message = "옵션 이름은 필수 입력 항목입니다.")
    @Size(max = 50, message = "옵션 이름은 최대 50자까지 입력할 수 있습니다.")
    @Pattern(regexp = "^[\\p{L}0-9 ()\\[\\]+\\-&/_]+$", message = "옵션 이름에 사용 가능한 특수문자는 ( ), [ ], +, -, &, /, _ 입니다")
    private String name;

    @NotNull(message = "수량은 필수 입력 항목입니다.")
    @DecimalMin(value = "1", message = "수량은 최소 1 이상이어야 합니다.")
    private Long quantity;

    public OptionDTO() {
    }

    private OptionDTO(OptionDTOBuilder builder) {
        this.name = builder.name;
        this.quantity = builder.quantity;
    }

    public String getName() {
        return name;
    }

    public Long getQuantity() {
        return quantity;
    }

    public static class OptionDTOBuilder {
        private String name;
        private Long quantity;

        public OptionDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public OptionDTOBuilder quantity(Long quantity) {
            this.quantity = quantity;
            return this;
        }

        public OptionDTO build() {
            return new OptionDTO(this);
        }
    }

    public Option toEntity() {
        return new Option.OptionBuilder()
            .name(this.name)
            .quantity(this.quantity)
            .build();
    }
}
