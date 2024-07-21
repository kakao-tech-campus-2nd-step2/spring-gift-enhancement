package gift.service;

import gift.dto.OptionResponseDto;
import gift.entity.Option;
import gift.entity.Product;
import gift.repository.OptionRepository;

import java.util.List;

public class OptionService {
    private final OptionRepository optionRepository;

    public OptionService(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    public OptionResponseDto getById(Long optionId) {
        return this.fromEntity(optionRepository.findById(optionId).get());
    }

    public List<OptionResponseDto> getAllOptions() {
        return optionRepository.findAll().stream().map(this::fromEntity).toList();
    }

    public OptionResponseDto save(Option option, Product product) {
        if (checkValidOptionName(option.getName(), product) &&
                checkValidOptionQuantity(option.getQuantity())) {
            return fromEntity(optionRepository.save(option));
        }
        return null;
    }

    public void subtract(Option option, Long quantity) {
        Option actualOption = optionRepository.findById(option.getId()).get();
        Long afterSubtractQuantity = actualOption.getQuantity() - quantity;
        Option newOption = new Option(actualOption.getId(), actualOption.getName(), afterSubtractQuantity, actualOption.getProduct());
        optionRepository.save(newOption);
    }

    public void delete(Option option) {
        optionRepository.delete(option);
    }

    public void update(Option option) {
        optionRepository.save(option);
    }

    public OptionResponseDto getOptionByName(String optionName) {
        return fromEntity(optionRepository.findOptionByName(optionName));
    }

    public OptionResponseDto fromEntity(Option option) {
        return new OptionResponseDto(option.getId(),option.getName(),option.getQuantity(),option.getProduct().getId());
    }

    public boolean checkValidOptionName(String optionName, Product product) {
        if (checkNameLength(optionName, 1, 50) &&
                checkNameCharIsOkay(optionName) &&
                checkNameRedundancy(optionName, product)) {
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
        return name.length() <= maxLen && name.length() >= minLen;
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
