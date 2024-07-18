package gift.controller;

import gift.dto.OptionDto;
import gift.service.OptionService;
import gift.vo.Option;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products/")
public class OptionController {

    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    /**
     *
     * @param id ProductId
     * @return List<Option>
     */
    @GetMapping("/{id}/options")
    public ResponseEntity<List<Option>> getOption(@PathVariable Long id) {
        List<Option> allOptions = optionService.getOptionsPerProduct(id);
        return new ResponseEntity<>(allOptions, HttpStatus.OK);
    }

    @PostMapping("/options")
    public ResponseEntity<Void> addOption(@Valid @RequestBody OptionDto optionDto) {
        optionService.addOption(optionDto);
        return ResponseEntity.noContent().build();
    }

}
