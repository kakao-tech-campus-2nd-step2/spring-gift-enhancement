package gift.option.domain;

public class OptionDTO {
    private String name;
    private Long quantity;
    private Long productId;


    // constructor
    public OptionDTO() {
    }

    public OptionDTO(String name, Long quantity, Long productId) {
        this.name = name;
        this.quantity = quantity;
        this.productId = productId;
    }

    //getter and setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
