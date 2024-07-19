package gift.main.dto;

import gift.main.Exception.CustomException;
import gift.main.Exception.ErrorCode;

public record OptionNameRequest(String name) {

    public OptionNameRequest {
        if (name.isBlank()) {
            throw new CustomException(ErrorCode.EMPTY_OPTION);
        }
        if (name.length() > 50) {
            throw new CustomException(ErrorCode.INVALID_OPTION_NAME_LENGT);
        }

        if (!name.matches("^[가-힣a-zA-Z0-9 ()\\[\\]\\+\\-&/_]*$")) {
            throw new CustomException(ErrorCode.INVALID_OPTION_NAME_CHARACTERS);
        }

    }
}
