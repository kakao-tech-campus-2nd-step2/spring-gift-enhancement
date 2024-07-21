package gift.web.dto.response.product;

import gift.domain.Product;
import gift.web.dto.response.category.ReadCategoryResponse;
import java.net.URL;

public class UpdateProductResponse {

    private final Long id;
    private final String name;
    private final Integer price;
    private final URL imageUrl;
    private final ReadCategoryResponse category;

    private UpdateProductResponse(Long id, String name, Integer price, URL imageUrl,
        ReadCategoryResponse category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    public static UpdateProductResponse from(Product product) {
        return new UpdateProductResponse(product.getId(), product.getName(), product.getPrice(),
            product.getImageUrl(), ReadCategoryResponse.fromEntity(product.getCategory()));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public URL getImageUrl() {
        return imageUrl;
    }

    public ReadCategoryResponse getCategory() {
        return category;
    }
}
