package gift.dto;

import gift.entity.Category;
import gift.entity.Option;
import gift.entity.Product;

import java.util.List;

public class ProductResponseDto {

    private final String name;
    private final String url;
    private final Integer price;
    public List<Option> options;
    private Long id;
    private Category category;

    public ProductResponseDto(Long id, String name, Integer price, String url, Category category, List<Option> options) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.url = url;
        this.category = category;
        this.options = options;
    }

    public ProductResponseDto(String name, Integer price, String url, Category category, List<Option> options) {
        this.name = name;
        this.price = price;
        this.url = url;
        this.category = category;
        this.options = options;
    }

    public static ProductResponseDto fromEntity(Product product) {
        return new ProductResponseDto(product.getName(), product.getPrice(), product.getUrl(), product.getCategory(), product.getOptions());
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

    public List<Option> getOptions() {
        return options;
    }

}
