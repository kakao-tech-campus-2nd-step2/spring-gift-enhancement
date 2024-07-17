package gift.product.model;

public class ProductResponseDto {

    private Long id;
    private String name;
    private Integer price;
    private String imageUrl;
    private Long categoryId;

    public ProductResponseDto() {
    }

    public ProductResponseDto(Long id, String name, Integer price, String imageUrl,
        Long categoryId) {
        this.imageUrl = imageUrl;
        this.price = price;
        this.name = name;
        this.id = id;
        this.categoryId = categoryId;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public static ProductResponseDto from(Product product) {
        return new ProductResponseDto(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getImageUrl(),
            product.getCategory().getId()
        );
    }
}
