package gift.service;

import gift.exception.ErrorCode;
import gift.exception.customException.CustomDuplicateException;
import gift.exception.customException.CustomNotFoundException;
import gift.model.item.Item;
import gift.model.option.Option;
import gift.model.option.OptionDTO;
import gift.repository.ItemRepository;
import gift.repository.OptionRepository;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class OptionService {

    private OptionRepository optionRepository;
    private ItemRepository itemRepository;

    public OptionService(OptionRepository optionRepository, ItemRepository itemRepository) {
        this.optionRepository = optionRepository;
        this.itemRepository = itemRepository;
    }

    public OptionDTO insertOption(Long itemId, OptionDTO optionDTO) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new CustomNotFoundException(
            ErrorCode.ITEM_NOT_FOUND));
        Option option = new Option(optionDTO.getName(), optionDTO.getQuantity(), item);
        return optionRepository.save(option).toDTO();
    }

    public void insertOptionList(Long itemId, List<OptionDTO> options, BindingResult result)
        throws CustomDuplicateException {
        if (new HashSet<>(
            options.stream().map(OptionDTO::getName).collect(Collectors.toSet())).size()
            != options.size()) {
            result.rejectValue("options", "", ErrorCode.DUPLICATE_NAME.getMessage());
            throw new CustomDuplicateException(result, ErrorCode.DUPLICATE_NAME);
        }
        Item item = itemRepository.findById(itemId)
            .orElseThrow(() -> new CustomNotFoundException(ErrorCode.OPTION_NOT_FOUND));
        optionRepository.saveAll(
            options.stream().map(o -> new Option(o.getName(), o.getQuantity(), item)).collect(
                Collectors.toSet()));
    }


    public List<OptionDTO> getOptionList(Long itemId) {
        if (!itemRepository.existsById(itemId)) {
            throw new CustomNotFoundException(ErrorCode.ITEM_NOT_FOUND);
        }
        return optionRepository.findAllByItemId(itemId).stream().map(Option::toDTO).toList();
    }

    public boolean isDuplicateName(String name) {
        return optionRepository.existsByName(name);
    }

    public OptionDTO updateOption(Long itemId, OptionDTO optionDTO) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new CustomNotFoundException(
            ErrorCode.ITEM_NOT_FOUND));
        if (!optionRepository.existsById(optionDTO.getId())) {
            throw new CustomNotFoundException(ErrorCode.OPTION_NOT_FOUND);
        }
        Option option = new Option(itemId, optionDTO.getName(), optionDTO.getQuantity(), item);
        return optionRepository.save(option).toDTO();
    }

    public void deleteOption(Long optionId) {
        if (!optionRepository.existsById(optionId)) {
            throw new CustomNotFoundException(ErrorCode.OPTION_NOT_FOUND);
        }
        optionRepository.deleteById(optionId);
    }

}
