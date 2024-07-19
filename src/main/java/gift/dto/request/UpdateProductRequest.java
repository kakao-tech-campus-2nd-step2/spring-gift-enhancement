package gift.dto.request;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import static gift.constant.Message.LENGTH_ERROR_MSG;
import static gift.constant.Message.POSITIVE_NUMBER_REQUIRED_MSG;

public class UpdateProductRequest {

    @Size(max = 15, message = LENGTH_ERROR_MSG)
    private String name;

    @Positive(message = POSITIVE_NUMBER_REQUIRED_MSG)
    private int price;

    private String imageUrl;

    private String category;

    public UpdateProductRequest(String name, int price, String imageUrl, String category) {
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
