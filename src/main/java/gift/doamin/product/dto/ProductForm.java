package gift.doamin.product.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.List;

public class ProductForm {

    private Long userId;

    @NotNull
    private Long category_id;

    @Pattern(regexp = "^[a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣0-9 ()\\[\\]+\\-&/_]{1,15}$", message = "영문, 한글, 숫자, 공백, 특수문자 ()[]+-&/_ 1자 이상 15자 미만으로 입력해야 합니다.")
    private String name;

    @NotNull
    @PositiveOrZero
    private Integer price;

    @NotNull
    private String imageUrl;

    @NotEmpty
    @Valid
    private List<OptionForm> options;

    public ProductForm(Long category_id, String name, Integer price, String imageUrl,
        List<OptionForm> options) {
        this.category_id = category_id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.options = options;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getCategory_id() {
        return category_id;
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

    public List<OptionForm> getOptions() {
        return options;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
