package gift.dto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ProductDto {

    private String name;
    private Integer price;
    private String imageUrl;
    private Long categoryId;
    private Long optionId;

    public ProductDto() {
    }

    public ProductDto(String name, Integer price, String imageUrl, Long categoryId) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
    }

    public ProductDto(String name, Integer price, String imageUrl, Long categoryId, Long optionId) {
        new ProductDto(name, price, imageUrl, categoryId);
        this.optionId = optionId;
    }

    public String getName() {
        return name;
    }

    public ProductDto setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getPrice() {
        return price;
    }

    public ProductDto setPrice(Integer price) {
        this.price = price;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ProductDto setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public ProductDto setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public Long getOptionId() {
        return optionId;
    }

    public ProductDto setOptionId(Long optionId) {
        this.optionId = optionId;
        return this;
    }
}

