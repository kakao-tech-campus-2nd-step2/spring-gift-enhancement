package gift.controller.product.dto;

import gift.service.product.dto.CategoryCommand;

public class CategoryRequest {

    public record Register(
        String name
    ) {

        public CategoryCommand.Register toCommand() {
            return new CategoryCommand.Register(name);
        }
    }

    public record Update(
        String name
    ) {

        public CategoryCommand.Update toCommand() {
            return new CategoryCommand.Update(name);
        }
    }


}
