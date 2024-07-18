package gift.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

@Entity(name = "options")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50)
    @NotNull
    @NotBlank
    @Length(max = 50)
    @Pattern(regexp = "[a-zA-Z0-9가-힣\\(\\)\\[\\]\\-+&_\\/\\s]+", message = "옵션 이름에는 (), [], -, +, &, _, /, 공백을 제외한 특수 문자를 사용할 수 없습니다.")
    private String name;
    @Column(nullable = false)
    private Long quantity;

    public Option() {
    }

    public Option(Long id, String name, Long quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public Option(String name, Long quantity) {
        this.name = name;
        this.quantity = quantity;
    }
}
