package gift.dto;

import gift.model.CategoryName;
import gift.model.ProductName;

public class InputProductDTO {
    private String name;
    private Integer price;
    private String imageUrl;
    private String category;

    public InputProductDTO(String name, int price, String imageUrl, String category) {
        this.setName(name);
        this.price = price;
        this.imageUrl = imageUrl;
        this.setCategory(category);
    }

    public void setName(String name) {
        ProductName productName = new ProductName(name);
        this.name = productName.getName();
    }

    public void setCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            this.category = null;
        }
        try {
            CategoryName categoryName = new CategoryName(category);
            this.category = categoryName.getName();
        } catch (Exception e) {
            this.category = null;
        }
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "SaveProductDTO{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                ", category='" + category + '\'' +
                '}';
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

    public String getCategory() {
        return category;
    }
}
