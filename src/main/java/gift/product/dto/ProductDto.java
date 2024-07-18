package gift.product.dto;

public class ProductDto {
    private Long productId;
    private String name;
    private int price;
    private String imgUrl;
    private Long categoryId;

    // 기본 생성자
    public ProductDto() {
    }

    // 모든 필드를 인자로 받는 생성자
    public ProductDto(Long productId, String name, int price, String imgUrl, Long categoryId) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
        this.categoryId = categoryId;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Long getCategoryId() {
        return categoryId;
    }
}