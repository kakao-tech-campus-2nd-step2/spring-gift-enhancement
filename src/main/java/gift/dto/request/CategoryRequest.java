package gift.dto.request;

import gift.domain.Category;
import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(
        @NotBlank(message = "카테고리 이름은 공백일 수 없습니다.")
        String name,
        String color,
        String imageUrl,
        String description) {
    public Category toEntity(){
        return new Category.Builder()
                .name(this.name)
                .color(this.color)
                .imageUrl(this.imageUrl)
                .description(this.description)
                .build();
    }
}
