package gift.option.model;

import gift.common.exception.OptionException;
import gift.option.OptionErrorCode;
import java.util.List;

public class Options {

    private final List<Option> options;

    public Options(List<Option> options) {
        this.options = options;
    }

    public void validateOptionSize() throws OptionException {
        if (options.size() <= 1) {
            throw new OptionException(OptionErrorCode.OPTION_COUNT_ONE);
        }
    }
}
