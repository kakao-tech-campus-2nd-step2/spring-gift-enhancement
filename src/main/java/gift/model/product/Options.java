package gift.model.product;

import java.util.ArrayList;
import java.util.List;

public class Options {

    private List<Option> options;

    public Options(List<Option> options) {
        validateOptions(options);
        this.options = new ArrayList<>(options);
    }

    private static void validateOptions(List<Option> options) {
        if (options.stream().map(Option::getName).distinct().count() != options.size()) {
            throw new IllegalArgumentException("Option name already exists");
        }
    }

    public void validateUniqueName(Option option) {
        for (Option o : options) {
            if (o.isSameName(option.getName())) {
                throw new IllegalArgumentException("Option name already exists");
            }
        }
        if (options.stream().anyMatch(o -> o.isSameName(option.getName()))) {
            throw new IllegalArgumentException("Option name already exists");
        }
    }

}
