package gift.dto;

import gift.entity.Category;

public class CategoryRequest {

    private String name;
    private String color;
    private String imgUrl;
    private String description;

    public CategoryRequest() {
    }

    public CategoryRequest(String name, String color, String imgUrl, String description) {
        this.name = name;
        this.color = color;
        this.imgUrl = imgUrl;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Category toEntity(CategoryRequest categoryRequest) {
        return new Category(categoryRequest.getName(), categoryRequest.getColor(),
            categoryRequest.getImgUrl(), categoryRequest.getDescription());
    }
}
