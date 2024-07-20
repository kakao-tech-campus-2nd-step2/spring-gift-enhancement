package gift.controller;

import gift.dto.OptionRequestDto;
import gift.dto.OptionResponseDto;
import gift.entity.Option;
import gift.service.OptionService;
import gift.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products/{productId}/options")
public class OptionController {

    private final ProductService productService;
    private OptionService optionService;

    public OptionController(OptionService optionService, ProductService productService) {
        this.optionService = optionService;
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<OptionResponseDto>> getOptions(@PathVariable Long productId) {
        List<OptionResponseDto> options = optionService.findAllByProductId(productId);
        return new ResponseEntity<>(options, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addOption(@PathVariable Long productId, @Valid @RequestBody OptionRequestDto optionRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Option option = optionRequestDto.toEntity(productService.findById(productId).orElseThrow(() -> new IllegalArgumentException("올바르지 않은 상품입니다.")));
        Option savedOption = optionService.save(option);
        OptionResponseDto optionResponseDto = new OptionResponseDto(savedOption.getId(), savedOption.getName(),savedOption.getQuantity());
        return new ResponseEntity<>(optionResponseDto, HttpStatus.CREATED);
    }


    @PutMapping("/{optionId}")
    public ResponseEntity<?> updateOption(@PathVariable Long productId, @PathVariable Long optionId, @Valid @RequestBody OptionRequestDto optionRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Option option = optionService.findById(optionId).orElseThrow(() -> new IllegalArgumentException("올바르지 않은 옵션"));
        option.setName(optionRequestDto.getName());
        option.setQuantity(optionRequestDto.getQuantity());
        optionService.save(option);
        OptionResponseDto optionResponseDto = new OptionResponseDto(option.getId(), option.getName(), option.getQuantity());

        return new ResponseEntity<>(optionResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{optionId}")
    public ResponseEntity<?> deleteOption(@PathVariable Long optionId) {
        optionService.deleteById(optionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
