package gift.dto;

public class OptionResponseDto {
    private Long id;
    private String name;
    private Long quantity;
    private Long product_id;

    public OptionResponseDto(Long id, String name, Long quantity, Long product_id) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.product_id = product_id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getQuantity() {
        return quantity;
    }

    public Long getProduct_id() {
        return product_id;
    }
}
