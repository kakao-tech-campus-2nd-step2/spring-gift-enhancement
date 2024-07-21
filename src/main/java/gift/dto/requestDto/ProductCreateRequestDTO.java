package gift.dto.requestDto;

import jakarta.validation.Valid;

public record ProductCreateRequestDTO(
    @Valid ProductRequestDTO productRequestDTO,
    @Valid OptionCreateRequestDTO optionCreateRequestDTO
) {
}
