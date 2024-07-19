package gift.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Pattern(regexp = "^[\\w\\s()\\[\\]+\\-&/]+$")
    private String name;

    @Min(1)
    @Max(99999999)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

}
