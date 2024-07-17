package gift.dto.request;

import jakarta.validation.constraints.*;

import static gift.constant.Message.*;

public class AddProductRequest {

    @NotBlank(message = REQUIRED_FIELD_MSG)
    @Size(max = 15, message = LENGTH_ERROR_MSG)
    private String name;

    @NotNull(message = REQUIRED_FIELD_MSG)
    @Positive(message = POSITIVE_NUMBER_REQUIRED_MSG)
    private int price;

    @NotBlank(message = REQUIRED_FIELD_MSG)
    private String imageUrl;

    @NotBlank(message = REQUIRED_FIELD_MSG)
    private String categoryName;

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
