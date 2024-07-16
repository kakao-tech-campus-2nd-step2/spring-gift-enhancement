package gift.model.category;

import jakarta.validation.constraints.NotBlank;

public record CartegoryRequest(
    Long id,
    @NotBlank
    String name,
    String color,
    String description) {

}
