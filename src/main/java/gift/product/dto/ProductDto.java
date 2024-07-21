package gift.product.dto;

public class ProductDto {

    private Long id;
    private String name;
    private int price;
    private String imgUrl;
    private Long categoryId;

    public ProductDto() {
    }

    // 모든 필드를 초기화하는 생성자
    public ProductDto(Long id, String name, int price, String imgUrl, Long categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
        this.categoryId = categoryId;
    }

    // 필요한 필드만 초기화하는 생성자
    public ProductDto(String name, int price, String imgUrl, Long categoryId) {
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public Long getCategoryId() {
        return categoryId;
    }
}