package gift.controller;

import gift.exception.ErrorCode;
import gift.exception.customException.CustomArgumentNotValidException;
import gift.exception.customException.CustomDuplicateException;
import gift.model.option.OptionDTO;
import gift.service.OptionService;
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

    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<OptionDTO>> getOptionList(@PathVariable("id") Long id) {
        List<OptionDTO> list = optionService.getOptionList(id);
        return ResponseEntity.ok(list);
    }

    @PostMapping("/{id}")
    public ResponseEntity<OptionDTO> createOption(@Valid @RequestBody OptionDTO optionDTO,
        @PathVariable("id") Long itemId, BindingResult result)
        throws CustomArgumentNotValidException {
        if (result.hasErrors()) {
            throw new CustomArgumentNotValidException(result, ErrorCode.INVALID_INPUT);
        }
        if (optionService.isDuplicateName(optionDTO.getName())) {
            throw new CustomDuplicateException(result, ErrorCode.DUPLICATE_NAME);
        }
        return ResponseEntity.ok(optionService.insertOption(itemId, optionDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OptionDTO> updateOption(@Valid @RequestBody OptionDTO optionDTO,
        @PathVariable("id") Long itemId, BindingResult result)
        throws CustomArgumentNotValidException {
        if (result.hasErrors()) {
            throw new CustomArgumentNotValidException(result, ErrorCode.INVALID_INPUT);
        }

        if (optionService.isDuplicateName(optionDTO.getName())) {
            throw new CustomDuplicateException(result, ErrorCode.DUPLICATE_NAME);
        }
        return ResponseEntity.ok(optionService.updateOption(itemId, optionDTO));
    }

    @DeleteMapping("/{option_id}")
    public ResponseEntity<Long> deleteOption(@PathVariable("option_id") Long optionId) {
        return ResponseEntity.ok(optionId);
    }

}
