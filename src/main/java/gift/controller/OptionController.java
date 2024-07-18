package gift.controller;

import gift.common.dto.PageResponse;
import gift.model.category.CategoryRequest;
import gift.model.category.CategoryResponse;
import gift.model.option.CreateOptionRequest;
import gift.model.option.CreateOptionResponse;
import gift.model.option.OptionResponse;
import gift.model.option.UpdateOptionRequest;
import gift.service.OptionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
public class OptionController {

    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @PostMapping("{productId}/options")
    public ResponseEntity<CreateOptionResponse> registerOption(
        @PathVariable("productId") Long productId,
        @Valid @RequestBody CreateOptionRequest request
    ) {
        CreateOptionResponse response = optionService.register(productId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("{productId}/options")
    public ResponseEntity<PageResponse<OptionResponse>> getAllOption(
        @PathVariable("productId") Long productId,
        @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        PageResponse<OptionResponse> response = optionService.getAllProductOptions(productId,
            pageable);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("{productId}/options/{optionId}")
    public ResponseEntity<OptionResponse> getOption(
        @PathVariable("productId") Long productId,
        @PathVariable("optionId") Long optionId
    ) {
        OptionResponse response = optionService.findOption(optionId);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/{productId}/options/{optionId}")
    public ResponseEntity<OptionResponse> updateOption(
        @PathVariable("productId") Long productId,
        @PathVariable("optionId") Long optionId,
        @Valid @RequestBody UpdateOptionRequest request) {
        OptionResponse response = optionService.updateOption(productId, optionId, request);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{productId}/options/{optionId}")
    public ResponseEntity deleteOption(
        @PathVariable("productId") Long productId,
        @PathVariable("optionId") Long optionId)
    {
        optionService.deleteOption(productId, optionId);
        return ResponseEntity.noContent().build();
    }
}
