package gift.dto;

public class ProductResponseDto {

    private final Long id;
    private final String name;
    private final double price;
    private final String imageUrl;
    private final Long categoryId;

    public ProductResponseDto(Long id, String name, double price, String imageUrl,
        Long categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Long getCategoryId() {
        return categoryId;
    }
}