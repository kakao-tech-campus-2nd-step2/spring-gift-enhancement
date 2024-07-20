package gift.doamin.product.controller;

import gift.doamin.product.dto.OptionForm;
import gift.doamin.product.service.OptionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products/{productId}/options")
public class OptionsController {

    private final OptionService optionService;

    public OptionsController(OptionService optionService) {
        this.optionService = optionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addOption(@PathVariable Long productId, @Valid @RequestBody OptionForm optionForm) {
        optionService.create(productId, optionForm);
    }

    @PutMapping("/{optionId}")
    public void updateOption(@PathVariable Long productId, @PathVariable Long optionId,
        @Valid @RequestBody OptionForm optionForm) {
        optionService.update(productId, optionId, optionForm);
    }
}
