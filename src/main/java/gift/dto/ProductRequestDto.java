package gift.dto;

import gift.entity.Product;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.Length;

public class ProductRequestDto {


    @NotNull(message = "이름은 필수입니다.")
    @Length(min = 1, max=15, message = "최대 15자까지 가능합니다.")
    @Pattern(regexp ="^[a-zA-Z0-9가-힣\\(\\)\\[\\]\\+\\-\\&\\/\\_]*$",message="허용되지 않는 특수 문자가 포함되어 있습니다.")
    @Pattern(regexp = "^(?!.*카카오).*$", message = "카카오가 포함된 문구는 담당 MD와 협의한 경우에만 사용 할 수 있습니다.")
    private String name;

    @NotNull(message = "가격은 필수입니다.")
    @Min(value = 0, message = "가격은 0보다 커야합니다.")
    @Max(value = Integer.MAX_VALUE, message = "가격이 너무 큽니다")
    @PositiveOrZero(message="가격은 숫자만 가능합니다. ")
    private int price;

    private String imageUrl;

    public ProductRequestDto() {
    }

    public ProductRequestDto(String name, int price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
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

    public Product toEntity() {
        return new Product(name, price, imageUrl);
    }
}