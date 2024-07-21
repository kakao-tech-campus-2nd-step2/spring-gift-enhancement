package gift.dto;

import gift.entity.Category;
import org.springframework.http.HttpStatus;

import java.util.List;

public class CategoryResponseDto {
    private Long id;
    private String name;
    private String color;
    private String description;
    private String imageUrl;
    private List<Category> categoryList;
    private HttpStatus httpStatus;

    public CategoryResponseDto(Long id, String name, String color, String description, String imageUrl, HttpStatus httpStatus) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.description = description;
        this.imageUrl = imageUrl;
        this.httpStatus = httpStatus;
    }

    public CategoryResponseDto(Long id, String name, String color, String description, String imageUrl) {
        this(id, name, color, description, imageUrl, null);
    }

    public CategoryResponseDto(List<Category> categoryList, HttpStatus httpStatus) {
        this.categoryList = categoryList;
        this.httpStatus = httpStatus;
    }

    public CategoryResponseDto(List<Category> categoryList) {
        this(categoryList, null);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

}
