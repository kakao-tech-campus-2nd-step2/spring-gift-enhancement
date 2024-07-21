package gift.dto;

import gift.entity.Wish;
import org.springframework.http.HttpStatus;

import java.util.Base64;
import java.util.List;

public class WishResponseDto {
    private Long id;
    private Long productId;
    private String tokenValue;

    public WishResponseDto(Long id, Long productId, String tokenValue) {
        this.id = id;
        this.productId = productId;
        this.tokenValue = tokenValue;
    }

    public WishResponseDto(Long productId, String tokenValue) {
        this(null, productId, tokenValue);
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
