package gift.option.model;

import gift.common.exception.OptionException;
import java.util.List;

public class Options {

    private List<Option> options;

    public Options(List<Option> options) {
        this.options = options;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void validate(Option option) throws OptionException {
        options.forEach((it) -> it.validateDuplicated(option));
    }
}
