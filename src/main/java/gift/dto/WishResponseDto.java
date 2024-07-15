package gift.dto;

import gift.entity.Wish;

import java.util.Base64;

public class WishResponseDto {
    private final Long id;
    private final Long productId;
    private final String tokenValue;

    public WishResponseDto(Long id, Long productId, String tokenValue) {
        this.id = id;
        this.productId = productId;
        this.tokenValue = tokenValue;
    }

    private static String makeTokenFrom(Long userId) {
        return Base64.getEncoder().encodeToString(userId.toString().getBytes());
    }

    public static WishResponseDto fromEntity(Wish wish) {
        String token = makeTokenFrom(wish.getUserId());
        return new WishResponseDto(wish.getId(), wish.getProductId(), token);
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public String getTokenValue() {
        return tokenValue;
    }


}
