package gift.domain.product.dto;

public class ProductResponse {

    private Long id;
    private String name;
    private int price;
    private String imageUrl;
    private Long categoryId;

    public ProductResponse() {
    }

    public ProductResponse(String name, int price, String imageUrl, Long categoryId) {
        this(null, name, price, imageUrl, categoryId);
    }

    public ProductResponse(Long id, String name, int price, String imageUrl, Long categoryId) {
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

    public int getPrice() {
        return this.price;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public Long getCategoryId() {
        return categoryId;
    }
}
