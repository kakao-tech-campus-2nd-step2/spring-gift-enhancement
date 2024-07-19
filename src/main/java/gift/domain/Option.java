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

    @Min(1)
    @Max(99999999)
    @NotNull(message = "옵션 수량은 필수 항목입니다.")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}
