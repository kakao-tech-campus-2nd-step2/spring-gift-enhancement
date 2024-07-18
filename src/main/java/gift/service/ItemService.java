package gift.service;

import gift.exception.ErrorCode;
import gift.exception.customException.CustomNotFoundException;
import gift.model.categories.Category;
import gift.model.item.Item;
import gift.model.item.ItemDTO;
import gift.model.item.ItemForm;
import gift.repository.CategoryRepository;
import gift.repository.ItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    public ItemService(ItemRepository itemRepository, CategoryRepository categoryRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public Long insertItem(ItemForm form) {
        Category category = categoryRepository.findById(form.getCategoryId())
            .orElseThrow(() -> new CustomNotFoundException(ErrorCode.CATEGORY_NOT_FOUND));
        Item item = new Item(form.getName(), form.getPrice(), form.getImgUrl(), category);
        return itemRepository.save(item).getId();
    }

    @Transactional(readOnly = true)
    public ItemDTO findItem(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new CustomNotFoundException(
            ErrorCode.ITEM_NOT_FOUND));
        return item.toDTO();
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
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
}
