package gift.product.model.dto.product;

import gift.product.model.dto.option.Option;
import gift.product.model.dto.option.OptionResponse;
import java.util.List;
import java.util.stream.Collectors;

public class ProductResponse {
    private final Long id;
    private final String name;
    private final int price;
    private final String imageUrl;
    private final Long categoryId;
    private final Long wishCount;
    private final List<OptionResponse> options;


    public ProductResponse(Product product, List<Option> options, Long wishCount) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.imageUrl = product.getImageUrl();
        this.categoryId = product.getCategory().getId();
        this.wishCount = wishCount;
        this.options = options.stream()
                .map(o -> new OptionResponse(o.getId(), o.getName(), o.getQuantity(), o.getAdditionalCost()))
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Long getWishCount() {
        return wishCount;
    }

    public List<OptionResponse> getOptions() {
        return options;
    }
}
