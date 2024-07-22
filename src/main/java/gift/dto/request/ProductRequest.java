package gift.dto.request;

import jakarta.validation.constraints.*;

import static gift.constant.Message.*;

public class ProductRequest {

    @NotBlank(message = REQUIRED_FIELD_MSG)
    @Size(max = 15, message = LENGTH_ERROR_MSG)
    @Pattern(regexp = "^[a-zA-Z0-9가-힣 ()\\[\\]+\\-&/_]*$", message = SPECIAL_CHAR_ERROR_MSG)
    private String name;

    @NotNull(message = REQUIRED_FIELD_MSG)
    @Positive(message = POSITIVE_NUMBER_REQUIRED_MSG)
    private int price;

    @NotBlank(message = REQUIRED_FIELD_MSG)
    private String imageUrl;

    @NotBlank(message = REQUIRED_FIELD_MSG)
    private String category;

    public ProductRequest(String name, int price, String imageUrl, String category) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCategory() {
        return category;
    }
}
