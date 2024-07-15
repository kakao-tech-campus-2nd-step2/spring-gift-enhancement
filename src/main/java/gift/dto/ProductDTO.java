package gift.dto;

import jakarta.validation.constraints.Size;

public class ProductDTO {

    private Long id;
    private String name;
    private int price;
    private String imageUrl;
    private int category;

    public ProductDTO() {
    }

    // Getters
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
    public int getCategory() {return category;}

    // Setters
    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public void setCategory(int category) {this.category = category;}
}

