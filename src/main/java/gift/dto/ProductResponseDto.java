package gift.dto;

import gift.entity.Category;
import gift.entity.Product;

public class ProductResponseDto {

    private final String name;
    private final String url;
    private final Integer price;
    private Long id;
    private Category category;

    public ProductResponseDto(Long id, String name, Integer price, String url, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.url = url;
        this.category=category;
    }

    public ProductResponseDto(String name, Integer price, String url, Category category) {
        this.name = name;
        this.price = price;
        this.url = url;
        this.category=category;
    }

    public static ProductResponseDto fromEntity(Product product) {
        return new ProductResponseDto(product.getName(), product.getPrice(), product.getUrl(), product.getCategory());
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

    public String getUrl() {
        return url;
    }

}
