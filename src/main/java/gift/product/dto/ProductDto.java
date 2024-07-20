package gift.product.dto;

public class ProductDto {

    private Long id;
    private String name;
    private int price;
    private String imgUrl;
    private Long categoryId;

    // 기본 생성자
    public ProductDto() {
    }

    public void ProductDTO(Long id, String name, int price, String imageUrl, Long categoryId){
        this.id = id;
        this.name = name;
        this.price = price;
        this.imgUrl = imageUrl;
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