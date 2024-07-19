package gift.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "product_option")  // 테이블 이름 변경
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "옵션 이름은 필수 입력 항목입니다.")
    @Size(max = 50, message = "옵션 이름은 최대 50자까지 입력할 수 있습니다.")
    @Pattern(regexp = "^[\\p{L}0-9 ()\\[\\]+\\-&/_]+$", message = "옵션 이름에 사용 가능한 특수문자는 ( ), [ ], +, -, &, /, _ 입니다")
    private String name;

    @NotNull(message = "수량은 필수 입력 항목입니다.")
    @DecimalMin(value = "1", message = "수량은 최소 1 이상이어야 합니다.")
    private Long quantity;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    protected Option() {
    }

    private Option(OptionBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.quantity = builder.quantity;
        this.product = builder.product;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }

    public static class OptionBuilder {
        private Long id;
        private String name;
        private Long quantity;
        private Product product;

        public OptionBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public OptionBuilder name(String name) {
            this.name = name;
            return this;
        }

        public OptionBuilder quantity(Long quantity) {
            this.quantity = quantity;
            return this;
        }

        public OptionBuilder product(Product product) {
            this.product = product;
            return this;
        }

        public Option build() {
            return new Option(this);
        }
    }
}
