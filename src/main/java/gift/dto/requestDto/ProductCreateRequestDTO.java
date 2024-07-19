package gift.dto.requestDto;

public record ProductCreateRequestDTO(
    ProductRequestDTO productRequestDTO, OptionCreateRequestDTO optionCreateRequestDTO
) {
}
