package gift.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryRequest (String name, String color, String imageUrl, String description){ }
