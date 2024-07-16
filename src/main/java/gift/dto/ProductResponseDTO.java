package gift.dto;

import gift.model.Category;

public record ProductResponseDTO(Long id,
                                 String name,
                                 Long price,
                                 String imageUrl,
                                 Category category) {
}
