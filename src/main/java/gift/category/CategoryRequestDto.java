package gift.category;

public class CategoryRequestDto {
    private String name;
    private String color;
    private String imageUrl;
    private String description;

    public CategoryRequestDto() {
    }

    public Category toEntity(){
        return new Category(
            name,
            color,
            imageUrl,
            description
        );
    }
}
