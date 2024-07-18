package gift.model.product;

import gift.model.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import java.util.regex.Pattern;

@Entity
public class Option extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String name;

    @NotNull
    @Column(nullable = false)
    private Integer count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    protected Option() {
    }

    public Option(String name, Integer count, Product product) {
        OptionNameValidator.isValidName(name);
        OptionNameValidator.isValidCount(count);
        this.name = name;
        this.count = count;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getCount() {
        return count;
    }

    private static class OptionNameValidator {

        private static final int MAX_LENGTH = 50;
        private static final Pattern VALID_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9가-힣\\(\\)\\[\\]\\+\\-\\&\\/\\_\\s]*$");

        public static boolean isValidName(String value) {
            if (value.isBlank()) {
                throw new IllegalArgumentException("옵션 이름은 필수 입력값입니다.");
            }

            if (value.length() > MAX_LENGTH) {
                throw new IllegalArgumentException("옵션 이름은 최대 50자까지 입력 가능합니다.");
            }

            if (!VALID_PATTERN.matcher(value).matches()) {
                throw new IllegalArgumentException(
                    "옵션 이름은 영문, 한글, 숫자, 공백,  ( ), [ ], +, -, &, /, _만 입력 가능합니다.");
            }

            return true;
        }

        public static boolean isValidCount(Integer value) {
            if (value < 0) {
                throw new IllegalArgumentException("옵션 개수는 1 이상의 값이어야 합니다.");
            }

            if (value > 100_000_000) {
                throw new IllegalArgumentException("옵션 개수는 1억개 이하의 값이어야 합니다.");
            }
            return true;
        }
    }
}