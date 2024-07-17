package gift.Model;

import jakarta.validation.constraints.NotNull;

public record RequestCategory(
        @NotNull
        String name,
        @NotNull
        String color,
        @NotNull
        String imageUrl,
        String description
){ }
