package gift.dto;

public class ProductDto {
    private Long id;
    private String name;
    private int price;
    private String imgUrl;

    // jpa나 jackson이 객체를 생성할 때 기본 생성자를 필요로 한다?
    public ProductDto() {
    }

    public ProductDto(Long id, String name, int price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public ProductDto(String name, int price, String imgUrl) {
        this.id = null;
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
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
}