package gift.product.business.pojo;

import gift.product.business.dto.OptionIn;
import gift.product.persistence.entity.Option;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PojoOptions {

    private Map<Long, Option> optionMap;

    public PojoOptions(List<Option> options) {
        this.optionMap = options.stream()
            .collect(Collectors.toMap(Option::getId, Function.identity()));
    }

    public List<Option> getOptions() {
        return List.copyOf(optionMap.values());
    }

    public void checkWithExist(List<OptionIn.Create> optionInCreates) {
        for (var optionInCreate : optionInCreates) {
            if (optionMap.values().stream()
                .anyMatch(option -> option.getName().equals(optionInCreate.name()))) {
                throw new IllegalArgumentException("옵션 이름이 중복되었습니다.");
            }
        }
    }

    public void updateOptions(List<OptionIn.Update> optionInUpdates) {
        for (var optionInUpdate : optionInUpdates) {
            var option = optionMap.get(optionInUpdate.id());
            option.update(optionInUpdate.name(), optionInUpdate.quantity());
        }
        checkDuplicate();
    }

    private void checkDuplicate() {
        int size = optionMap.size();

        if (size != optionMap.values().stream()
            .map(Option::getName)
            .collect(Collectors.toSet())
            .size()) {
            throw new IllegalArgumentException("옵션 이름이 중복되었습니다.");
        }
    }
}
