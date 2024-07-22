package gift.dto;

import gift.entity.Wish;

public record WishDTO(ProductResponseDTO productResponseDTO, Integer quantity) {

    public static WishDTO convertToWishDTO(Wish wish) {
        return new WishDTO(ProductResponseDTO.convertToProductResponseDTO(wish.getProduct()), wish.getQuantity());
    }


}