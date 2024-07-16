package gift.DTO.Product;

import gift.domain.Category;
import gift.domain.Product;

public class ProductResponse {
    Long id;
    String name;
    int price;
    String imageUrl;
    Category category;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.imageUrl = product.getImageUrl();
        this.category = product.getCategory();
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
  
    public String getImageUrl() {
        return imageUrl;
    }
  
    public Category getCategory(){
        return category;
    }
}
