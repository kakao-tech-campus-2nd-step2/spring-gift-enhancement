package gift.web.controller;

import gift.domain.option.Option;
import gift.service.option.OptionService;
import gift.web.dto.OptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class OptionController {
    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @PostMapping("/{productId}")
    public ResponseEntity<OptionDto> createOption(@PathVariable Long productId, @RequestBody OptionDto optionDto) {
        return new ResponseEntity<>(optionService.createOption(productId, optionDto), HttpStatus.CREATED);
    }
}
