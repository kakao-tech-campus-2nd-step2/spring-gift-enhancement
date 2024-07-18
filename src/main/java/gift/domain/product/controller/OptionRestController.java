package gift.domain.product.controller;

import gift.domain.product.dto.OptionDto;
import gift.domain.product.service.OptionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products/{productId}/options")
public class OptionRestController {

    private final OptionService optionService;

    public OptionRestController(OptionService optionService) {
        this.optionService = optionService;
    }

    @PostMapping
    public ResponseEntity<OptionDto> create(@PathVariable("productId") long productId, @RequestBody @Valid OptionDto optionDto) {
        OptionDto optionResponseDto = optionService.create(productId, optionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(optionResponseDto);
    }
}
