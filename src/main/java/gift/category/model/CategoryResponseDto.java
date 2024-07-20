package gift.category.model;

public record CategoryResponseDto(Long id, String name, String color, String imageUrl,
                                  String description) {

    public static CategoryResponseDto from(Category category) {
        return new CategoryResponseDto(
            category.getId(),
            category.getName(),
            category.getColor(),
            category.getImageUrl(),
            category.getDescription()
        );
    }
}
