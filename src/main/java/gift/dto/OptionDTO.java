package gift.dto;

import gift.entity.Option;
import gift.entity.Product;

public class OptionDTO {

    private String name;

    private int quantity;

    private Long productId;

    public OptionDTO() {
    }

    public OptionDTO(String name, int quantity, Long productId) {
        this.name = name;
        this.quantity = quantity;
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public Option toEntity(Product product) {
        return new Option(name, quantity, product);
    }
}
