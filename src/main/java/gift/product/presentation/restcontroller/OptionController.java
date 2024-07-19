package gift.product.presentation.restcontroller;

import gift.product.business.service.OptionService;
import gift.product.presentation.dto.RequestOptionCreateDto;
import gift.product.presentation.dto.RequestOptionUpdateDto;
import gift.product.presentation.dto.ResponseOptionDto;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PostMapping("/{id}/options")
    public ResponseEntity<List<Long>> createOption(
        @RequestBody @Valid List<RequestOptionCreateDto> requestOptionsDtos,
        @PathVariable("id") Long productId) {
        var optionRegisterDtos = requestOptionsDtos.stream()
            .map(RequestOptionCreateDto::toOptionRegisterDto)
            .toList();
        var optionIds = optionService.createOption(optionRegisterDtos, productId);
        return ResponseEntity.ok(optionIds);
    }

    @GetMapping("/{id}/options")
    public ResponseEntity<List<ResponseOptionDto>> getOptionsByProduct(
        @PathVariable("id") Long productId) {
        var responseOptionDtos = optionService.getOptionsByProduct(productId).stream()
            .map(ResponseOptionDto::from)
            .toList();
        return ResponseEntity.ok(responseOptionDtos);
    }

    @PutMapping("/{id}/options")
    public ResponseEntity<List<Long>> updateOption(
        @RequestBody @Valid List<RequestOptionUpdateDto> requestOptionsDto,
        @PathVariable("id") Long productId) {
        var optionUpdateDtos = requestOptionsDto.stream()
            .map(RequestOptionUpdateDto::toOptionUpdateDto)
            .toList();

        var optionIds = optionService.updateOptions(optionUpdateDtos, productId);
        return ResponseEntity.ok(optionIds);
    }

    @DeleteMapping("/options")
    public ResponseEntity<List<Long>> deleteOption(@RequestBody List<Long> optionIds) {
        optionService.deleteOptions(optionIds);
        return ResponseEntity.ok(optionIds);
    }
}
