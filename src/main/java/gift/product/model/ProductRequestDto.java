package gift.product.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ProductRequestDto {

    private Long id;

    @NotBlank
    @Size(min = 1, max = 15, message = "상품 이름은 공백을 포함하여 최대 15자입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣 \\(\\)\\[\\]\\+\\-\\&/_]*$", message = "사용가능한 특수 문자는 (),[],+,-,&,/,_ 입니다.")
    private String name;

    @NotNull
    @Min(value = 0, message = "가격은 0원 이상입니다.")
    private int price;

    @NotBlank
    private String imageUrl;

    @NotNull
    private Long categoryId;

    public ProductRequestDto() {
    }

    public ProductRequestDto(String name, int price, String imageUrl, Long categoryId) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
    }

    private ProductRequestDto(Long id, String name, int price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public static ProductRequestDto from(ProductResponseDto productResponseDto) {
        return new ProductRequestDto(
            productResponseDto.getId(),
            productResponseDto.getName(),
            productResponseDto.getPrice(),
            productResponseDto.getImageUrl()
        );
    }
}
