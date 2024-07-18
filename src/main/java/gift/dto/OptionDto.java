package gift.dto;

import gift.entity.Option;

public class OptionDto {

    private Long id;
    private String name;
    private int quantity;
    private Long productId;

    protected OptionDto() {
    }

    public OptionDto(Option option) {
        this(option.getId(), option.getName(), option.getQuantity(), option.getProduct().getId());
    }

    public OptionDto(Long id, String name, int quantity, Long productId) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.productId = productId;
    }

    public Long getId() {
        return id;
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
}
