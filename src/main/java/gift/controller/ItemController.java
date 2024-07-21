package gift.controller;

import gift.exception.ErrorCode;
import gift.exception.customException.CustomArgumentNotValidException;
import gift.model.item.ItemDTO;
import gift.model.item.ItemForm;
import gift.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/list")
    public ResponseEntity<Page<ItemDTO>> getItemList(
        @PageableDefault(size = 5, sort = "id", direction = Direction.DESC) Pageable pageable) {
        Page<ItemDTO> list = itemService.getList(pageable);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/category/{category_id}")
    public ResponseEntity<Page<ItemDTO>> getItemListByCategory(
        @PathVariable("category_id") Long categoryId,
        @PageableDefault(size = 5, sort = "id", direction = Direction.DESC) Pageable pageable) {
        Page<ItemDTO> list = itemService.getListByCategoryId(categoryId, pageable);
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<Long> createItem(@Valid @RequestBody ItemForm form, BindingResult result)
        throws MethodArgumentNotValidException {
        if (result.hasErrors()) {
            throw new CustomArgumentNotValidException(result, ErrorCode.INVALID_INPUT);
        }
        ItemDTO itemDTO = new ItemDTO(form.getName(), form.getPrice(), form.getImgUrl(),
            form.getCategoryId());
        Long itemId = itemService.insertItem(itemDTO,form.getOptions(),result);
        return ResponseEntity.ok(itemId);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Long> updateItem(@PathVariable Long id, @Valid @RequestBody ItemForm form,
        BindingResult result) throws MethodArgumentNotValidException {
        if (result.hasErrors()) {
            throw new CustomArgumentNotValidException(result, ErrorCode.BAD_REQUEST);
        }
        ItemDTO itemDTO = new ItemDTO(id, form.getName(), form.getPrice(), form.getImgUrl(),
            form.getCategoryId());
        return ResponseEntity.ok(itemService.updateItem(itemDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.ok(id);
    }
}