package gift.response;

import gift.model.Product;
import java.util.List;

public record ProductOptionsResponse(Product product, List<OptionResponse> options) {

}
