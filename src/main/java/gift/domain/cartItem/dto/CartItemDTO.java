package gift.domain.cartItem.dto;

public class CartItemDTO {

    Long id; // cartItem id;
    Long productId;
    String name;
    Integer price;
    String imageUrl;
    Integer count;

    public CartItemDTO(Long id, Long productId, String name, Integer price, String imageUrl,
        Integer count) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.count = count;
    }
}
