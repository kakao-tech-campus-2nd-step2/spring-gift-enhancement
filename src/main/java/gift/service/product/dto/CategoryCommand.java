package gift.service.product.dto;

import gift.model.product.Category;

public class CategoryCommand {

    public record Register(
        String name
    ) {

        public Category toEntity() {
            return new Category(name);
        }
    }

    public record Update(
        String name
    ) {

    }

}
