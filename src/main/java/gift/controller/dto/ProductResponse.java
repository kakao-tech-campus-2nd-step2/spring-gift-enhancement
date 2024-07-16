package gift.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class ProductResponse {
    private Long id;
    @NotEmpty(message = "Product name cannot be empty")
    @Pattern(
        regexp = "^[a-zA-Z0-9 ()\\[\\]+\\-&/_]{1,15}$",
        message = "상품 이름은 공백을 포함하여 최대 15자까지 입력할 수 있다 해당 특수문자 사용가능 : ( ), [ ], +, -, &, /, _"
    )
    private String name;
    private Double price;

    private String imageUrl;

    private Long categoryId;

    private String categoryName;

    public ProductResponse() {
    }

    public ProductResponse(Long id, String name, Double price, String imageUrl, Long categoryId,
        String categoryName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
