package gift.controller;

import gift.controller.dto.OptionRequest;
import gift.controller.dto.OptionResponse;
import gift.service.OptionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/products/option")
public class OptionController {
    OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @PostMapping
    public ResponseEntity<OptionResponse> createOption(@Valid @RequestBody OptionRequest optionRequest){
        OptionResponse optionResponse = optionService.addOption(optionRequest);
        return ResponseEntity.ok(optionResponse);
    }

    @DeleteMapping
    public ResponseEntity<Long> deleteOption(@Valid @RequestBody Long id){
        Long deletId = optionService.deleteOption(id);
        return ResponseEntity.ok(deletId);
    }
}
