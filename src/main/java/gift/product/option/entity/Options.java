package gift.product.option.entity;

import gift.exception.CustomException;
import gift.exception.ErrorCode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Options {

    private List<Option> options;

    public Options(List<Option> options) {
        this.options = new ArrayList<>(options);
    }

    public void validate(Option other) {
        if (options.stream()
            .map(Option::getName)
            .collect(Collectors.toSet())
            .contains(other.getName())) {
            throw new CustomException(ErrorCode.OPTION_NAME_DUPLICATE);
        }
    }

}
