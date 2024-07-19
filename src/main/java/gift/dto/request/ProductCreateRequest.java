package gift.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ProductCreateRequest {
    
    private Long id;

    @Size(max = 15, message = "Product name is too long!")
    @Pattern(regexp = "^[a-zA-Z0-9 ()\\[\\]+\\-\\&\\/\\_가-힣]*$", message = "Product name has invalid character")
    @Pattern(regexp = "^(?!.*카카오).*$", message = "'카카오'가 포함된 문구는 담당 MD와 협의한 경우에만 사용 가능합니다")
    private String productName;
    private int price;
    private String imageUrl;
    private String category;

    @Size(max = 50, message = "Option name is too long!")
    @Pattern(regexp = "^[a-zA-Z0-9 ()\\[\\]+\\-\\&\\/\\_가-힣]*$", message = "Option name has invalid character")
    private String optionName;

    @Size(min = 1, max = 99999999, message = "option length is invalid")
    private int quantity;

    public ProductCreateRequest(String productName, int price, String imageUrl, String category, String optionName, int quantity) {
        this.productName = productName;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
        this.optionName = optionName;
        this.quantity = quantity;
    }

    public Long getId(){
        return id;
    }

    public String getProductName(){
        return productName;
    }

    public int getPrice(){
        return price;
    }

    public String getImageUrl(){
        return imageUrl;
    }

    public String getCategory(){
        return category;
    }

    public String getOptionName(){
        return optionName;
    }

    public int getQuantity(){
        return quantity;
    }
}
