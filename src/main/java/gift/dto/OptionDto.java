package gift.dto;

import gift.validation.ValidName;
import gift.vo.Option;
import gift.vo.Product;
import jakarta.validation.constraints.NotEmpty;

public record OptionDto (
    Long id,

    Long productId,

    @ValidName
    @NotEmpty(message = "옵션명을 입력해 주세요.")
    String name,

    int quantity
){
    public Option toOption (Product product) {
        return new Option(id, product, name, quantity);
    }
}