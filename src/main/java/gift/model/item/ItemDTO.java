package gift.model.item;

public class ItemDTO {

    private final Long id;
    private final String name;
    private final Long price;
    private final String imgUrl;
    private final Long categoryId;

    public ItemDTO(Long id, String name, Long price, String imgUrl, Long categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
        this.categoryId = categoryId;
    }

    public ItemDTO(String name, Long price, String imgUrl, Long categoryId) {
        this(null, name, price, imgUrl, categoryId);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Long getCategoryId() {
        return categoryId;
    }
}