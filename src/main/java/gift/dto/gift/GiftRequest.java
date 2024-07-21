package gift.dto.gift;

import gift.dto.option.OptionRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public class GiftRequest {

    @Size(max = 15)
    @Pattern(regexp = "[\\s\\(\\)\\[\\]\\+\\-&/_a-zA-Z0-9\uAC00-\uD7AF]*", message = "특수문자 오류")
    private String name;

    private int price;

    private String imageUrl;

    private Long categoryId;

    @NotEmpty(message = "옵션은 최소 하나 이상 포함되어야 합니다.")
    @Valid
    private List<OptionRequest> options;

    public GiftRequest(String name, int price, String imageUrl, Long categoryId, List<OptionRequest> options) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
        this.options = options;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public List<OptionRequest> getOptions() {
        return options;
    }

    public void setOptions(List<OptionRequest> options) {
        this.options = options;
    }

}

