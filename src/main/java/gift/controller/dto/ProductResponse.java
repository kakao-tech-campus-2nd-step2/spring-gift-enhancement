package gift.controller.dto;

import gift.domain.Category;
import gift.domain.Option;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import java.util.List;

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

    private Category category;

    private List<Option> optionList;

    public ProductResponse() {
    }

    public ProductResponse(Long id, String name, Double price, String imageUrl, Category category,
        List<Option> optionList) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
        this.optionList = optionList;
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
