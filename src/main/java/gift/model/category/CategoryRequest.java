package gift.model.category;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(
    Long id,
    @NotBlank
    String name,
    String color,
    String imageUrl,
    String description) {


}
