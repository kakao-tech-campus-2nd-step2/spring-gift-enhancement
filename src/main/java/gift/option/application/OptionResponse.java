package gift.option.application;

import gift.option.domain.Option;

public record OptionResponse (
        Long id,
        String name,
        Integer quantity
){
    public static OptionResponse from(Option option) {
        return new OptionResponse(
                option.getId(),
                option.getName(),
                option.getQuantity()
        );
    }
}
