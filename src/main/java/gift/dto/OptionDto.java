package gift.dto;

import gift.vo.Option;
import gift.vo.Product;

public record OptionDto (
    Long id,

    Product product,

    String name,

    int quantity
){
    public Option toOption (Product product) {
        return new Option(id, product, name, quantity);
    }
}