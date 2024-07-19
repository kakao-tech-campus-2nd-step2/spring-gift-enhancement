package gift.product.presentation.dto;

import gift.product.business.dto.OptionRegisterDto;
import java.util.List;

public record RequestOptionsDto(
    List<RequestOptionDto> options,
    Long productId
) {

    public List<OptionRegisterDto> toOptionRegisterDtos() {
        return options.stream()
            .map(option -> new OptionRegisterDto(
                option.name(),
                option.quantity()
            ))
            .toList();
    }
}
