package gift.main.dto;

import gift.main.Exception.CustomException;
import gift.main.Exception.ErrorCode;

import java.util.Objects;

public record OptionRequest(
        String name,
        int quantity) {

    public OptionRequest {
        if (name.isBlank()) {
            throw new CustomException(ErrorCode.EMPTY_OPTION);
        }
        if (name.length() > 50) {
            throw new CustomException(ErrorCode.INVALID_OPTION_NAME_LENGT);
        }

        if (!name.matches("^[가-힣a-zA-Z0-9 ()\\[\\]\\+\\-&/_]*$")) {
            throw new CustomException(ErrorCode.INVALID_OPTION_NAME_CHARACTERS);
        }

        if (quantity < 1 || quantity >  100000000) {
            throw new CustomException(ErrorCode.INVALID_OPTION_QUANTITY);
        }

    }


    @Override

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OptionRequest that = (OptionRequest) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
