package gift.service;

import gift.entity.Option;
import gift.entity.Product;
import gift.repository.OptionRepositoryInterface;

import java.util.List;

public class OptionService {
    private OptionRepositoryInterface optionRepositoryInterface;

    public OptionService(OptionRepositoryInterface optionRepositoryInterface) {
        this.optionRepositoryInterface = optionRepositoryInterface;
    }

    public Option getById(Long optionId) {
        return optionRepositoryInterface.findById(optionId).get();
    }

    public List<Option> getAllOptions() {
        return optionRepositoryInterface.findAll();
    }

    public Option save(Option option,Product product) {
        if ( checkValidOptionName(option.getName(),product) &&
                checkValidOptionQuantity(option.getQuantity())){
            return optionRepositoryInterface.save(option);
        }
        return null;
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

    public boolean checkValidOptionName(String optionName,Product product) {
        if (checkNameLength(optionName, 1, 50) &&
                checkNameCharIsOkay(optionName) &&
                checkNameRedundancy(optionName,product)) {
            return true;
        }
        throw new IllegalArgumentException();
    }


    public boolean checkValidOptionQuantity(Long optionQuantity) {
        if (optionQuantity < 100000000 && optionQuantity > 1) {
            return true;
        }
        throw new IllegalArgumentException();
    }

    public boolean checkNameLength(String name, int maxLen, int minLen) {
        if (name.length() <= maxLen && name.length() >= minLen) {
            return true;
        }
        return false;
    }

    private boolean checkNameRedundancy(String optionName, Product product) {
        for (int i = 0; i < product.getOptions().size(); i++) {
            if (optionName.equals(product.getOptions().get(i).getName())) {
                return false;
            }
        }
        return true;
    }

    private boolean checkNameCharIsOkay(String optionName) {

        String okchar = "()[]+-&/_ ";
        for (char nameChar : optionName.toCharArray()) {
            if (okchar.contains(optionName) ||
                    Character.isAlphabetic(nameChar) ||
                    Character.isDigit(nameChar) ||
                    optionName.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
                return true;
            }
        }
        return false;
    }
}
