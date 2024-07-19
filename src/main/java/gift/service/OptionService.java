package gift.service;

import gift.entity.Option;
import gift.repository.OptionRepositoryInterface;

import java.util.List;

public class OptionService {
    private OptionRepositoryInterface optionRepositoryInterface;

    public OptionService(OptionRepositoryInterface optionRepositoryInterface) {
        this.optionRepositoryInterface = optionRepositoryInterface;
    }

    public Option getById(Long optionId) {
        return optionRepositoryInterface.findById(optionId);
    }
    public List<Option> getAllOptions() {
        return optionRepositoryInterface.findAll();
    }

    public Option save(Option option) {
        return optionRepositoryInterface.save(option);
    }

    public void delete(Option option) {
        optionRepositoryInterface.delete(option);
    }

    public Option update(Option option) {
        return optionRepositoryInterface.save(option);
    }
    public Option getOptionByName(String optionName) {
        return optionRepositoryInterface.findOptionByName(optionName);
    }

    public boolean checkValidOptionName(String optionName) {

    }

    public boolean checkValidOptionQuantity(Long optionQuantity){

    }


}
