package gift.dto;

import gift.vo.Category;

public record CategoryDto (
        Long id,
        String name,
        String color,
        String imageUrl,
        String description
) {
    public Category toCategory() {
        return new Category(id, name, color, imageUrl, description);
    }
}
