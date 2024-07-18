package gift.product.domain;

import java.util.List;

public class ProductDTO {

    private Long id;
    private String name;
    private Long price;
    private String imageUrl;
    private Long categoryId;

    private List<String> optionNameList;

    public ProductDTO() {
        id = 0L;
    }

    public ProductDTO(Long id, String name, Long price, String imageUrl, Long categoryId,
        List<String> optionNameList) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
        this.optionNameList = optionNameList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
