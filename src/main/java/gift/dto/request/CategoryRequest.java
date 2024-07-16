package gift.dto.request;

import gift.domain.Category;

public record CategoryRequest(String name, String color, String imageUrl, String description) {
    public Category toEntity(){
        return new Category.Builder()
                .name(this.name)
                .color(this.color)
                .imageUrl(this.imageUrl)
                .description(this.description)
                .build();
    }
}
