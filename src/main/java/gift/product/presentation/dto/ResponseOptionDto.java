package gift.product.presentation.dto;

import gift.product.business.dto.OptionDto;

public record ResponseOptionDto(
    Long id,
    String name,
    Integer quantity
) {

    public static ResponseOptionDto from(OptionDto optionDto) {
        return new ResponseOptionDto(
            optionDto.id(),
            optionDto.name(),
            optionDto.quantity()
        );
    }
}
