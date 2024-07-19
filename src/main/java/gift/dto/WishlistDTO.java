package gift.dto;

import gift.model.Wishlist;

import java.util.List;
import java.util.stream.Collectors;

public class WishlistDTO {
    private Long id;
    private Long productId;
    private String username;
    private int quantity;
    private String productName;
    private int price;
    private String imageUrl;
    private List<OptionDTO> options;

    public WishlistDTO() {}

    public WishlistDTO(Long id, Long productId, String username, int quantity, String productName, int price, String imageUrl) {
        this.id = id;
        this.productId = productId;
        this.username = username;
        this.quantity = quantity;
        this.productName = productName;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public String getUsername() {
        return username;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getProductName() {
        return productName;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<OptionDTO> getOptions() {
        return options;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setOptions(List<OptionDTO> options) {
        this.options = options;
    }

    public static WishlistDTO convertToDTO(Wishlist wishlist) {
        List<OptionDTO> optionDTOs = wishlist.getProduct().getOptions().stream()
            .map(OptionDTO::convertToDTO)
            .collect(Collectors.toList());

        WishlistDTO wishlistDTO = new WishlistDTO(
            wishlist.getId(),
            wishlist.getProduct().getId(),
            wishlist.getUser().getUsername(),
            wishlist.getQuantity(),
            wishlist.getProduct().getName(),
            wishlist.getPrice(),
            wishlist.getProduct().getImageUrl()
        );
        wishlistDTO.setOptions(optionDTOs);
        return wishlistDTO;
    }
}
