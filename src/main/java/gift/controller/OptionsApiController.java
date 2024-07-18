package gift.controller;

import gift.auth.CheckRole;
import gift.exception.InputException;
import gift.model.Options;
import gift.request.OptionsAddRequest;
import gift.response.OptionResponse;
import gift.service.OptionsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OptionsApiController {

    private final OptionsService optionsService;

    public OptionsApiController(OptionsService optionsService) {
        this.optionsService = optionsService;
    }

    @CheckRole("ROLE_ADMIN")
    @PostMapping("/api/products/{id}")
    public ResponseEntity<Void> addOptions(@PathVariable Long id, @RequestBody @Valid
    OptionsAddRequest dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InputException(bindingResult.getAllErrors());
        }

        optionsService.addOption(dto.optionName(), dto.quantity(), id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @CheckRole("ROLE_ADMIN")
    @PutMapping("/api/products/{id}")
    public ResponseEntity<Void> updateOptions(@PathVariable Long id,
        @RequestParam("option_id") Long optionId,
        @RequestBody @Valid OptionsAddRequest dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InputException(bindingResult.getAllErrors());
        }

        optionsService.updateOption(optionId, dto.optionName(), dto.quantity(), id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @CheckRole("ROLE_ADMIN")
    @DeleteMapping("/api/products/{id}")
    public ResponseEntity<Void> deleteOptions(@PathVariable Long id,
        @RequestParam("option_id") Long optionId) {
        optionsService.deleteOption(id, optionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
