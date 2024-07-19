package gift.product.business.dto;

import gift.product.persistence.entity.Option;
import java.util.List;

public record OptionDto(
    Long id,
    String name,
    Integer quantity
) {

    public static OptionDto from(Option option) {
        return new OptionDto(
            option.getId(),
            option.getName(),
            option.getQuantity()
        );
    }

    public static List<OptionDto> of(List<Option> options) {
        return options.stream()
            .map(OptionDto::from)
            .toList();
    }
}
