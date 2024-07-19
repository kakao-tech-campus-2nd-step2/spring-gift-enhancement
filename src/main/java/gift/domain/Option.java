package gift.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "옵션 이름은 필수 항목입니다.")
    @Pattern(regexp = "^[\\w\\s()\\[\\]+\\-&/]+$")
    private String name;

    @Min(value = 1, message = "옵션 수량은 최소 1개 이상이어야 합니다.")
    @Max(value = 99999999, message = "옵션 수량은 최대 1억 개 미만이어야 합니다.")
    @NotNull(message = "옵션 수량은 필수 항목입니다.")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}
