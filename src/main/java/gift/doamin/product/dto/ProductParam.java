package gift.doamin.product.dto;

import gift.doamin.product.entity.Product;

public class ProductParam {

    private Long id;

    private Long userId;

    private Long category_id;

    private String name;

    private Integer price;

    private String imageUrl;

    public ProductParam(Product product) {
        this.id = product.getId();
        this.userId = product.getUser().getId();
        this.category_id = product.getCategory().getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.imageUrl = product.getImageUrl();
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getCategory_id() {
        return category_id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
