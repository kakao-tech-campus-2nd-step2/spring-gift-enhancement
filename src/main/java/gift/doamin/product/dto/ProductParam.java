package gift.doamin.product.dto;

import gift.doamin.product.entity.Product;
import java.util.List;

public class ProductParam {

    private Long id;

    private Long userId;

    private Long categoryId;

    private String name;

    private Integer price;

    private String imageUrl;

    private List<OptionParam> options;

    public ProductParam(Product product) {
        this.id = product.getId();
        this.userId = product.getUser().getId();
        this.categoryId = product.getCategory().getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.imageUrl = product.getImageUrl();
        this.options = product.getOptions().stream()
            .map(option -> new OptionParam(option.getId(), option.getName()))
            .toList();
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getCategoryId() {
        return categoryId;
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

    public List<OptionParam> getOptions() {
        return options;
    }
}
