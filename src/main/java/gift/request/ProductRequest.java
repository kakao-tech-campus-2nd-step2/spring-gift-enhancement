package gift.request;

import gift.validation.product.KakaoNotAllowed;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public class ProductRequest {

    @NotBlank
    @Length(max = 15)
    @Pattern(regexp = "[a-zA-Z0-9가-힣 ()\\[\\]+\\-&/_]*")
    @KakaoNotAllowed
    private String name;

    @NotNull
    private Integer price;

    @NotBlank
    private String imageUrl;

    @NotNull
    private Long categoryId;

    @NotNull
    @Size(min = 1, message = "하나 이상의 옵션을 입력해야 합니다.")
    @Valid
    private List<OptionRequest> options;

    public ProductRequest(String name, Integer price, String imageUrl, Long categoryId) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public List<OptionRequest> getOptions() {
        return options;
    }

}
