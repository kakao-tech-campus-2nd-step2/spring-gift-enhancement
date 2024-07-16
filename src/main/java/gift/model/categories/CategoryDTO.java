package gift.model.categories;

public class CategoryDTO {

    private final Long id;
    private final String name;
    private final String imgUrl;

    public CategoryDTO(Long id, String name, String imgUrl) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Category toEntity() {
        return new Category(id, name, imgUrl);
    }
}
