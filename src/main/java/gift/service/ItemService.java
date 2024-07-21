package gift.service;

import gift.exception.ErrorCode;
import gift.exception.customException.CustomArgumentNotValidException;
import gift.exception.customException.CustomDuplicateException;
import gift.exception.customException.CustomNotFoundException;
import gift.model.categories.Category;
import gift.model.item.Item;
import gift.model.item.ItemDTO;
import gift.model.option.Option;
import gift.model.option.OptionDTO;
import gift.repository.CategoryRepository;
import gift.repository.ItemRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    public ItemService(ItemRepository itemRepository, CategoryRepository categoryRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public Long insertItem(ItemDTO itemDTO, List<OptionDTO> options, BindingResult result)
        throws CustomDuplicateException {
        Category category = categoryRepository.findById(itemDTO.getCategoryId())
            .orElseThrow(() -> new CustomNotFoundException(ErrorCode.CATEGORY_NOT_FOUND));
        Item item = new Item(itemDTO.getName(), itemDTO.getPrice(), itemDTO.getImgUrl(), category);

        validateOptions(options, result);

        item.getOptions().addAll(
            options.stream().map(o -> new Option(o.getName(), o.getQuantity(), item)).collect(
                Collectors.toSet()));

        return itemRepository.save(item).getId();
    }

    @Transactional
    public Long insertOption(Long itemId, OptionDTO optionDTO, BindingResult result)
        throws CustomDuplicateException {
        Item item = findItemById(itemId);
        if (item.getOptions().stream().map(Option::getName).toList()
            .contains(optionDTO.getName())) {
            result.rejectValue("name", "", ErrorCode.DUPLICATE_NAME.getMessage());
            throw new CustomDuplicateException(result, ErrorCode.DUPLICATE_NAME);
        }
        Option option = new Option(optionDTO.getName(), optionDTO.getQuantity(), item);
        item.getOptions().add(option);
        return itemRepository.save(item).getId();
    }

    @Transactional(readOnly = true)
    public Page<ItemDTO> getList(Pageable pageable) {
        Page<Item> list = itemRepository.findAll(pageable);
        return list.map(Item::toDTO);
    }

    @Transactional(readOnly = true)
    public Page<ItemDTO> getListByCategoryId(Long categoryId, Pageable pageable) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new CustomNotFoundException(ErrorCode.CATEGORY_NOT_FOUND);
        }
        Page<Item> list = itemRepository.findAllByCategoryId(categoryId, pageable);
        return list.map(Item::toDTO);
    }

    @Transactional
    public Long updateItem(ItemDTO itemDTO) {
        Category category = categoryRepository.findById(itemDTO.getCategoryId())
            .orElseThrow(() -> new CustomNotFoundException(ErrorCode.CATEGORY_NOT_FOUND));
        Item item = new Item(itemDTO.getId(), itemDTO.getName(), itemDTO.getPrice(),
            itemDTO.getImgUrl(), category);
        return itemRepository.save(item).getId();
    }

    @Transactional
    public Long updateOption(Long itemId, OptionDTO optionDTO, BindingResult result)
        throws CustomArgumentNotValidException {
        Item item = findItemById(itemId);
        Option option = item.getOptions().stream()
            .filter(o -> o.getId().equals(optionDTO.getId()))
            .findFirst()
            .orElseThrow(() -> new CustomNotFoundException(ErrorCode.OPTION_NOT_FOUND));
        if (!option.update(optionDTO.getName(), optionDTO.getQuantity())) {
            throw new CustomArgumentNotValidException(result, ErrorCode.INVALID_INPUT);
        }
        return itemRepository.save(item).getId();
    }

    @Transactional(readOnly = true)
    public List<OptionDTO> getOptionList(Long itemId) {
        Item item = findItemById(itemId);
        return item.getOptions().stream().map(Option::toDTO).toList();
    }

    @Transactional
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    @Transactional
    public void deleteOption(Long itemId, Long optionId) {
        Item item = findItemById(itemId);
        Option option = item.getOptions().stream()
            .filter(o -> o.getId().equals(optionId))
            .findFirst()
            .orElseThrow(() -> new CustomNotFoundException(ErrorCode.OPTION_NOT_FOUND));
        item.getOptions().remove(option);
        itemRepository.save(item);
    }

    @Transactional(readOnly = true)
    public Item findItemById(Long id) {
        return itemRepository.findById(id).orElseThrow(() -> new CustomNotFoundException(
            ErrorCode.ITEM_NOT_FOUND));
    }

    private boolean isDuplicateInOptionName(List<OptionDTO> options) {
        return options.stream()
            .map(OptionDTO::getName)
            .distinct()
            .count() != options.size();
    }

    private void validateOptions(List<OptionDTO> options, BindingResult result)
        throws CustomDuplicateException {
        if (isDuplicateInOptionName(options)) {
            result.rejectValue("options", "", ErrorCode.DUPLICATE_NAME.getMessage());
            throw new CustomDuplicateException(result, ErrorCode.DUPLICATE_NAME);
        }
    }
}
