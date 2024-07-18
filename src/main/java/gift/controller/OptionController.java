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
@RequestMapping("/api/v1/options")
public class OptionController {

    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @PostMapping("")
    public ResponseEntity<CreateOptionResponse> registerOption(@Valid @RequestBody
        CreateOptionRequest request) {
        CreateOptionResponse response = optionService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("")
    public ResponseEntity<PageResponse<OptionResponse>> getAllOption(
        @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        PageResponse<OptionResponse> response = optionService.findAllOption(pageable);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OptionResponse> getOption(@PathVariable("id") Long id) {
        OptionResponse response = optionService.findOption(id);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OptionResponse> updateOption(@PathVariable("id") Long id,
        @Valid @RequestBody UpdateOptionRequest request) {
        OptionResponse response = optionService.updateOption(id, request);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOption(@PathVariable("id") Long id) {
        optionService.deleteOption(id);
        return ResponseEntity.noContent().build();
    }
}
