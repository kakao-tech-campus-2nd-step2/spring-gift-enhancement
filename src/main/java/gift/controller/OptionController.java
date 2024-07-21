package gift.controller;

import gift.exception.ErrorCode;
import gift.exception.customException.CustomArgumentNotValidException;
import gift.model.option.OptionDTO;
import gift.service.ItemService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/option")
public class OptionController {

    private final ItemService itemService;

    public OptionController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<OptionDTO>> getOptionList(@PathVariable("id") Long id) {
        List<OptionDTO> list = itemService.getOptionList(id);
        return ResponseEntity.ok(list);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Long> createOption(@Valid @RequestBody OptionDTO optionDTO,
        @PathVariable("id") Long itemId, BindingResult result)
        throws CustomArgumentNotValidException {
        if (result.hasErrors()) {
            throw new CustomArgumentNotValidException(result, ErrorCode.INVALID_INPUT);
        }
        return ResponseEntity.ok(itemService.insertOption(itemId, optionDTO, result));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateOption(@Valid @RequestBody OptionDTO optionDTO,
        @PathVariable("id") Long itemId, BindingResult result)
        throws CustomArgumentNotValidException {
        if (result.hasErrors()) {
            throw new CustomArgumentNotValidException(result, ErrorCode.INVALID_INPUT);
        }
        return ResponseEntity.ok(itemService.updateOption(itemId, optionDTO, result));
    }

    @DeleteMapping("/{item_id}/{option_id}")
    public ResponseEntity<Long> deleteOption(@PathVariable("item_id") Long itemId,
        @PathVariable("option_id") Long optionId) {
        itemService.deleteOption(itemId, optionId);
        return ResponseEntity.ok(optionId);
    }

}
