package gift.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import gift.model.Category;
import gift.model.Product;
import gift.model.ProductName;

public class ProductDTO {
    @JsonProperty
    private long id;
    @JsonProperty
    private String name;
    @JsonProperty
    private Integer price;
    @JsonProperty
    private String imageUrl;
    @JsonProperty
    private Category category;

    public ProductDTO(long id, String name, int price, String imageUrl, Category category) {
        this.id = id;
        this.setName(name);
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    public void setName(String name) {
        ProductName productName = new ProductName(name);
        this.name = productName.getName();
    }

    public static ProductDTO getProductDTO(Product product){
        return new ProductDTO(product.getId(), product.getName(), product.getPrice(), product.getImageUrl(), product.getCategory());
    }

    public long getId() {
        return id;
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

    public Category getCategory() {
        return category;
    }
}
