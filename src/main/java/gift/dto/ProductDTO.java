package gift.dto;

import gift.model.Product;
import gift.service.CategoryService;

public class ProductDTO {

    private Long id;
    private String name;
    private int price;
    private String imageUrl;
    private int category;
    private String categoryName;

    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, int price, String imageUrl, int category, String categoryName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
        this.categoryName = categoryName;
    }

    // Getters and Setters

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

    public int getCategory() {
        return category;
    }

    public String getCategoryName() {
        return categoryName;
    }

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

    public void setCategory(int category) {
        this.category = category;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


    public static ProductDTO convertToDTO(Product product, CategoryService categoryService) {
        String categoryName = categoryService.getCategoryById(product.getCategory()).getName();
        return new ProductDTO(product.getId(), product.getName(), product.getPrice(), product.getImageUrl(), product.getCategory(), categoryName);
    }
}

