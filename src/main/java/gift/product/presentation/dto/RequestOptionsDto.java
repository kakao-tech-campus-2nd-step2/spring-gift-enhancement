package gift.product.presentation.dto;

import gift.product.business.dto.OptionRegisterDto;
import java.util.List;

public record RequestOptionsDto(
    List<RequestOptionCreateDto> options
) {

    public List<OptionRegisterDto> toOptionRegisterDtos() {
        return options.stream()
            .map(RequestOptionCreateDto::toOptionRegisterDto)
            .toList();
    }
}
