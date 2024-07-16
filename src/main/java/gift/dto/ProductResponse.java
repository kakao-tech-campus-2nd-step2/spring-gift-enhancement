package gift.dto;

import gift.entity.Product;

public class ProductResponse {

    private Long id;
    private String name;
    private int price;
    private String imgUrl;
    private String categoryName;

    public ProductResponse() {
    }

    public ProductResponse(Long id, String name, int price, String imgUrl, String categoryName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
        this.categoryName = categoryName;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public static ProductResponse from(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(),
            product.getImgUrl(), product.getCategory().getName());
    }
}
