package gift.dto;

import gift.entity.Wish;

public record WishDTO(ProductDTO productDTO, Integer quantity) {

    public static WishDTO convertToWishDTO(Wish wish) {
        return new WishDTO(ProductDTO.convertToProductDTO(wish.getProduct()), wish.getQuantity());
    }


}