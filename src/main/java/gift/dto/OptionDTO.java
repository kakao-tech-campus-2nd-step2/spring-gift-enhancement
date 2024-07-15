package gift.dto;

import gift.model.Option;

public class OptionDTO {

    private Long id;
    private String name;
    private int quantity;
    private int price;
    private Long productId;

    public OptionDTO() {
    }

    public OptionDTO(Long id, String name, int quantity, int price, Long productId) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
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

    public int getPrice() {
        return price;
    }

    public Long getProductId() {
        return productId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public static OptionDTO convertToDTO(Option option) {
        return new OptionDTO(option.getId(), option.getName(), option.getQuantity(), option.getPrice(), option.getProduct().getId());
    }
}
