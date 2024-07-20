package gift.DTO;

import java.util.List;

public record ProductAll(
        ProductDTO productDTO,
        List<ProductOptionDTO> productOptionDTOList
) {
}
