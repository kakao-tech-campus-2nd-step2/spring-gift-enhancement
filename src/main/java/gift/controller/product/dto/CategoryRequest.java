package gift.controller.product.dto;

import gift.service.product.dto.CategoryCommand;

public class CategoryRequest {

    public record Register(
        String name,
        String color,
        String description,
        String imageUrl
    ) {

        public CategoryCommand.Register toCommand() {
            return new CategoryCommand.Register(name, color, description, imageUrl);
        }
    }

    public record Update(
        String name,
        String color,
        String description,
        String imageUrl
    ) {

        public CategoryCommand.Update toCommand() {
            return new CategoryCommand.Update(name, color, description, imageUrl);
        }
    }


}
