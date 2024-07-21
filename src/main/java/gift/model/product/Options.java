package gift.model.product;

import gift.service.product.dto.OptionCommand.Update;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Options {

    private List<Option> options;

    public Options(List<Option> options) {
        validateOptions(options);
        this.options = new ArrayList<>(options);
    }

    public List<Option> getOptions() {
        return new ArrayList<>(options);
    }

    public boolean isDeletePossible() {
        return options.size() > 1;
    }

    public static void validateOptions(List<Option> options) {
        if (options.stream().map(Option::getName).distinct().count() != options.size()) {
            throw new IllegalArgumentException("중복된 option 이름이 존재합니다.");
        }
    }

    public Options merge(Options newOptions) {
        List<Option> mergedOptions = new ArrayList<>(options);
        mergedOptions.addAll(newOptions.getOptions());
        return new Options(mergedOptions);
    }

    public boolean isUpdatePossible(Option option, String name) {
        if (option.isSameName(name)) {
            return true;
        }
        if (options.stream().anyMatch(o -> o.getName().equals(name))) {
            throw new IllegalArgumentException("중복된 option 이름이 존재합니다.");
        }
        return true;
    }
}
