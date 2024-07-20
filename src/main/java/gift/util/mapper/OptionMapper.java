package gift.util.mapper;

import gift.product.option.dto.response.OptionResponse;
import gift.product.option.entity.Option;

public class OptionMapper {

    public static OptionResponse toResponse(Option option) {
        return new OptionResponse(option.getId(), option.getName(), option.getQuantity());
    }

}
