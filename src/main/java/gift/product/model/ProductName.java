package gift.product.model;

import gift.exception.KakaoProductException;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;

public class ProductName {

    @NotBlank(message = "상품명을 입력하세요.")
    @Size(max = 15, message = "상품명은 공백 포함 최대 15자")
    @Pattern(regexp = "^[\\w\\s()\\[\\]+\\-&/_]*$", message = "상품명에 잘못된 문자가 있습니다.")
    private String value;

    public ProductName(String value) {
        this.value = value;
        validate();
    }

    public String getValue() {
        return value;
    }

    // 상품명에 '카카오'가 포함된 경우
    public void validate() {
        if (value != null && value.contains("카카오")) {
            throw new KakaoProductException("상품명에 '카카오'가 포함된 경우 담당 MD에게 문의하세요.");
        }
    }
}