package gift.model.option;

public record CreateOptionResponse(Long id, String name, int quantity, Long productId) {

    public static CreateOptionResponse from(Option option) {
        return new CreateOptionResponse(option.getId(), option.getName(), option.getQuantity(),
            option.getProduct().getId());
    }
}
