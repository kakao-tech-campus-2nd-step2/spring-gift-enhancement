package gift.option.model;

public record OptionResponseDto(Long id, String name, Integer quantity) {

    public static OptionResponseDto from(Option option) {
        return new OptionResponseDto(option.getId(), option.getName(), option.getQuantity());
    }
}
