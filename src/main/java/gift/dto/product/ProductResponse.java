package gift.dto.product;

import java.util.List;

public record ProductResponse(
    Long id,
    String name,
    int price,
    String imageUrl,
    Long categoryId,
    List<Long> options
) {

}
