package gift.dto;

import gift.entity.Product;

public class ProductResponseDto {

    private final String name;
    private final String url;
    private final Long price;
    private Long id;

    public ProductResponseDto(Long id, String name, Long price, String url) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.url = url;
    }

    public ProductResponseDto(String name, Long price, String url) {
        this.name = name;
        this.price = price;
        this.url = url;
    }

    public static ProductResponseDto fromEntity(Product product) {
        return new ProductResponseDto(product.getName(), product.getPrice(), product.getUrl());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }

    public String getUrl() {
        return url;
    }

}
