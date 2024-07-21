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

    public CategoryResponseDto(Long id, String name, String color, String description, String imageUrl) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.description = description;
        this.imageUrl = imageUrl;
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

}
