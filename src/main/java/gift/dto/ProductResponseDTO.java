package gift.dto;

public record ProductResponseDTO(Long id,
                                 String name,
                                 int price,
                                 String imageUrl,
                                 String categoryName) {
}
