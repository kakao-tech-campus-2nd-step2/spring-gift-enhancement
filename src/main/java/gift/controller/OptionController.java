package gift.controller;

import gift.domain.model.dto.OptionRequestDto;
import gift.domain.model.dto.OptionResponseDto;
import gift.service.OptionService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products/{productId}/options")
@Validated
public class OptionController {

    private final OptionService OptionService;

    public OptionController(OptionService OptionService) {
        this.OptionService = OptionService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OptionResponseDto> getAllOptionsByProductId(@PathVariable Long productId) {
        return OptionService.getAllOptionsByProductId(productId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OptionResponseDto addOption(
        @PathVariable Long productId,
        @Valid @RequestBody OptionRequestDto optionRequestDto) {
        return OptionService.addOption(productId, optionRequestDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OptionResponseDto updateOption(
        @PathVariable Long id,
        @Valid @RequestBody OptionRequestDto optionRequestDto) {
        return OptionService.updateOption(id, optionRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOption(@PathVariable Long id) {
        OptionService.deleteOption(id);
    }
}