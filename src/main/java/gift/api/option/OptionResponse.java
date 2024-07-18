package gift.api.option;

public record OptionResponse(
    Long id,
    String name,
    Integer quantity
) {
    public static OptionResponse of(Option option) {
        return new OptionResponse(option.getId(),
                                option.getName(),
                                option.getQuantity());
    }
}
