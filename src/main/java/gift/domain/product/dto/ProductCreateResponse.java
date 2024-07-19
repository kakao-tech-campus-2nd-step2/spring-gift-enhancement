package gift.domain.product.dto;

import gift.domain.option.dto.OptionResponse;

public class ProductCreateResponse {
    private Long id;
    private String name;
    private int price;
    private String imageUrl;
    private Long categoryId;
    private OptionResponse optionResponse;

    public ProductCreateResponse(ProductResponse response, OptionResponse optionResponse) {
        this.id = response.getId();
        this.name = response.getName();
        this.price = response.getPrice();
        this.imageUrl = response.getImageUrl();
        this.categoryId = response.getCategoryId();
        this.optionResponse = optionResponse;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return this.price;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }


    public Long getCategoryId() {
        return categoryId;
    }

    public OptionResponse getOptionResponse() {
        return optionResponse;
    }
}
