package gift.model.gift;

import gift.model.category.Category;
import gift.model.category.CategoryResponse;

public class GiftResponse {

    private Long id;

    private String name;

    private int price;

    private String imageUrl;

    private CategoryResponse category;


    public GiftResponse(Long id, String name, int price, String imageUrl, CategoryResponse category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    public static GiftResponse from(Gift gift) {
        return new GiftResponse(gift.getId(), gift.getName(), gift.getPrice(), gift.getImageUrl(), CategoryResponse.fromEntity(gift.getCategory()));
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public CategoryResponse getCategory() {
        return category;
    }

    public void setCategory(CategoryResponse category) {
        this.category = category;
    }
}
