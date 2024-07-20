package gift.dto;

public class GiftOptionResponse {

    private Long id;
    private String name;
    private Long quantity;

    public GiftOptionResponse(Long id, String name, Long quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
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
}
