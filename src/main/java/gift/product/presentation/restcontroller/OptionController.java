package gift.product.presentation.restcontroller;

import gift.product.business.service.OptionService;
import gift.product.presentation.dto.RequestOptionsDto;
import gift.product.presentation.dto.ResponseOptionDto;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping("/{id}/options")
    public ResponseEntity<List<Long>> createOption(
        @RequestBody @Valid RequestOptionsDto requestOptionsDto,
        @PathVariable("id") Long productId) {
        var optionRegisterDtos = requestOptionsDto.toOptionRegisterDtos();
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
}
