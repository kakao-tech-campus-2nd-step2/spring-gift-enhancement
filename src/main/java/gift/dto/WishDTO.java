package gift.dto;

import gift.entity.Wish;
import jakarta.validation.constraints.Min;

public record WishDTO(ProductResponseDTO productResponseDTO, @Min(value = 1, message = "상품의 개수는 0보다 커야합니다.") Integer quantity) {

    public static WishDTO convertToWishDTO(Wish wish) {
        return new WishDTO(ProductResponseDTO.convertToProductResponseDTO(wish.getProduct()), wish.getQuantity());
    }


}