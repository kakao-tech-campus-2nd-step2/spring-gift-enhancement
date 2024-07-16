package gift.category;

public class CategoryDTO {

    private String name;

    public String getName() {
        return name;
    }

    public CategoryDTO() {
    }

    public CategoryDTO(String name) {
        this.name = name;
    }

    public Category toEntity() {
        return new Category(-1L, name);
    }
}
