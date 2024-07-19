package gift.option.model;

import gift.common.exception.OptionException;
import gift.option.OptionErrorCode;
import java.util.List;

public class Options {

    private List<Option> options;

    public Options(List<Option> options) {
        this.options = options;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void validateDuplicated(Option option) throws OptionException {
        options.forEach((it) -> it.validateDuplicated(option));
    }

    public void validateOptionSize() throws OptionException {
        if(options.size() <= 1) {
            throw new OptionException(OptionErrorCode.OPTION_COUNT_ONE);
        }
    }
}
