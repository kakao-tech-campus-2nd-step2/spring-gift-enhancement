package gift.model.gift;

import gift.model.category.CategoryResponse;
import gift.model.option.Option;
import gift.model.option.OptionResponse;

import java.util.List;
import java.util.stream.Collectors;

public class GiftResponse {

    private Long id;

    private String name;

    private int price;

    private String imageUrl;

    private CategoryResponse category;

    private List<OptionResponse> options;


    public GiftResponse(Long id, String name, int price, String imageUrl, CategoryResponse category, List<OptionResponse> options) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
        this.options = options;
    }

    public static GiftResponse from(Gift gift) {
        return new GiftResponse(gift.getId(),
                gift.getName(),
                gift.getPrice(),
                gift.getImageUrl(),
                CategoryResponse.fromEntity(gift.getCategory()),
                gift.getOptions().stream()
                        .map(OptionResponse::from)
                        .toList());
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

    public CategoryResponse getCategory() {
        return category;
    }

    public List<OptionResponse> getOptions() {
        return options;
    }
}
