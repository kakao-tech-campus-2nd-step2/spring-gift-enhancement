package gift.service;

import gift.entity.Option;
import gift.repository.OptionRepositoryInterface;

import java.util.List;

public class OptionService {
    private OptionRepositoryInterface optionRepositoryInterface;

    public OptionService(OptionRepositoryInterface optionRepositoryInterface) {
        this.optionRepositoryInterface = optionRepositoryInterface;
    }

    public List<Option> getAllOptions() {
        return optionRepositoryInterface.findAll();
    }

    public Option getOptionByName(String optionName) {
        return optionRepositoryInterface.findOptionByName(optionName);
    }

}
