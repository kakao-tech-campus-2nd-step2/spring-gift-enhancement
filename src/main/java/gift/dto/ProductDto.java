package gift.dto;

import gift.dto.request.OptionRequest;
import gift.dto.response.ProductResponse;

import java.util.List;

public class ProductDto {

    private Long id;
    private String name;
    private int price;
    private String imageUrl;
    private Long categoryId;
    private List<OptionRequest> options;

    public ProductDto(String name, int price, String imageUrl, Long categoryId) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
    }

    public ProductDto(String name, int price, String imageUrl, Long categoryId, List<OptionRequest> options) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
        this.options = options;
    }

    public ProductDto(Long id, String name, int price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public ProductResponse toResponseDto() {
        return new ProductResponse(this.id, this.name, this.price, this.imageUrl);
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

    public List<OptionRequest> getOptions() {
        return options;
    }

}
