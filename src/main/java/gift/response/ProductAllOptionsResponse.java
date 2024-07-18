package gift.response;

import gift.model.Options;
import gift.model.Product;
import java.util.List;

public record ProductAllOptionsResponse(Product product, List<Options> options) {

}
