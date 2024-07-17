package gift.controller.dto;

import gift.domain.Category;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.List;

public class ProductRequest {

    @NotEmpty(message = "Product name cannot be empty")
    @Pattern(
        regexp = "^[a-zA-Z0-9 ()\\[\\]+\\-&/_]{1,15}$",
        message = "상품 이름은 공백을 포함하여 최대 15자까지 입력할 수 있다 해당 특수문자 사용가능 : ( ), [ ], +, -, &, /, _"
    )
    private String name;
    @NotEmpty(message = "Price cannot be empty")
    private Double price;

    @NotEmpty(message = "Image URL cannot be empty")
    private String imageUrl;

    @NotEmpty(message = "Category ID cannot be empty")
    private Long categoryId;

    @NotEmpty(message = "Product must have at least one option")
    private List<ProductOptionRequest> options;



    public ProductRequest() {
    }

    public ProductRequest(String name, Double price, String imageUrl, Long categoryId) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public List<ProductOptionRequest> getOptions() {
        return options;
    }

    public void setOptions(List<ProductOptionRequest> options) {
        this.options = options;
    }
}
