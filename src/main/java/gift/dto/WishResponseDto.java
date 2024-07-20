package gift.dto;

import gift.entity.Wish;
import org.springframework.http.HttpStatus;

import java.util.Base64;
import java.util.List;

public class WishResponseDto {
    private Long id;
    private Long productId;
    private String tokenValue;
    private List<Wish> wishes;
    private HttpStatus httpStatus;

    public WishResponseDto(Long id, Long productId, String tokenValue, HttpStatus httpStatus) {
        this.id = id;
        this.productId = productId;
        this.tokenValue = tokenValue;
        this.httpStatus = httpStatus;
    }

    public WishResponseDto(Long productId, String tokenValue, HttpStatus httpStatus) {
        this(null, productId, tokenValue, httpStatus);
    }

    public WishResponseDto(Long productId, String tokenValue) {
        this(null, productId, tokenValue, null);
    }

    public WishResponseDto(List<Wish> wishes) {
        this.wishes = wishes;
    }

    public WishResponseDto(List<Wish> wishes, HttpStatus httpStatus) {
        this.wishes = wishes;
        this.httpStatus = httpStatus;
    }

    public WishResponseDto(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public WishResponseDto(Wish candidateWish) {
        this.wishes.add(candidateWish);
    }

    private static String makeTokenFrom(Long userId) {
        return Base64.getEncoder().encodeToString(userId.toString().getBytes());
    }

    public static WishResponseDto fromEntity(Wish wish) {
        String token = makeTokenFrom(wish.getUserId());
        return new WishResponseDto(wish.getProductId(), token);
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

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
