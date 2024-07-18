package gift.option;

import java.util.List;

public class Options {

    private List<Option> options;

    protected Options() {

    }

    public Options(List<Option> options) {
        this.options = options;
    }

    public void validate(Option option) {
        options.forEach((it) -> it.validateDuplicated(option));
    }
}
