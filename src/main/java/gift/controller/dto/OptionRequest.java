package gift.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class OptionRequest {
    @NotEmpty(message = "Product ID can not be empty")
    private Long productId;
    @NotEmpty(message = "Product Name can not be empty")
    private String name;
    @NotEmpty(message = "Product Quantity can not be empty")
    private int quantity;

    public OptionRequest(Long productId, String name, int quantity) {
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
