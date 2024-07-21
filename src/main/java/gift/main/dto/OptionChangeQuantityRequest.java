package gift.main.dto;

import gift.main.Exception.CustomException;
import gift.main.Exception.ErrorCode;

public record OptionChangeQuantityRequest(int quantity) {

    public OptionChangeQuantityRequest {
        if (quantity < 1 || quantity > 100000000) {
            throw new CustomException(ErrorCode.INVALID_OPTION_QUANTITY);
        }

    }
}
