package gift.option.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "options")
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long optionId;

    // entity에서도 validation 검증.
    @Column(nullable = false)
    @Pattern(regexp = "^[\\(\\)\\[\\]\\+\\-\\&\\/\\_\\p{Alnum}\\s\\uAC00-\\uD7A3]+$", message = "상품명에 ( ), [ ], +, -, &, /, _를 제외한 특수문자를 사용할 수 없습니다.")
    @Size(min = 1, max = 50)
    @NotBlank
    private String name;

    @Min(value = 0)
    @Max(value = 100_000_000)
    private int quantity;

    // 옵션이라는 것이 어떤 제품에 종속적이라고 생각해서 Option에도 productId를 추가하였습니다.
    // ex. 검정 반팔티의 옵션 (검정 반팔티 S, 검정 반팔티 M, 검정 반팔티 L, 검정 반팔티 XL)
    // 외래 키 제약 조건을 위해 Column 어노테이션을 사용합니다.
    @Column(name = "product_id")
    private long productId;

    public Option(Long optionId, String name, int quantity, long productId) {
        this.optionId = optionId;
        this.name = name;
        this.quantity = quantity;
        this.productId = productId;
    }

    public Option(String name, int quantity, long productId) {
        this(null, name, quantity, productId);
    }

    protected Option() {

    }

    // 수량 차감 메서드
    public void subtractQuantity() {
        if (quantity < 1) {
            throw new IllegalArgumentException("품절된 상품입니다.");
        }

        quantity--;
    }

    // 아직은 쓰진 않지만, 언젠간 필요할 것 같아서 만든 물량 채우기 메서드
    public void fillQuantity(int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException("수량을 추가할 땐 자연수를 입력해주세요.");
        }

        this.quantity += quantity;
    }

    public Long getOptionId() {
        return optionId;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public long getproductId() {
        return productId;
    }
}
