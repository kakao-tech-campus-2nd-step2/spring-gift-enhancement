package gift.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import gift.model.Product;

public record ProductResponse(Long id,
                              String name,
                              int price,
                              @JsonProperty(value = "image_url") String imageUrl,
                              @JsonProperty(value = "category_name") String categoryName) {

    public ProductResponse(Product product) {
        this(product.getId(),
            product.getName(),
            product.getPrice(),
            product.getImageUrl(),
            product.getCategory().getName()
        );
    }
}
