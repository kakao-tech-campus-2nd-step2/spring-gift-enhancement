package gift.product.controller;

import gift.product.dto.OptionDto;
import gift.product.service.OptionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products/{productId}/options")
public class OptionController {

  private final OptionService optionService;

  @Autowired
  public OptionController(OptionService optionService) {
    this.optionService = optionService;
  }

  @PostMapping
  public ResponseEntity<OptionDto> createOption(@PathVariable Long productId, @Valid @RequestBody OptionDto optionDto) {
    OptionDto createdOption = optionService.createOption(optionDto, productId);
    return new ResponseEntity<>(createdOption, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<OptionDto>> getAllOptions(@PathVariable Long productId) {
    List<OptionDto> options = optionService.getAllOptionsByProductId(productId);
    return ResponseEntity.ok(options);
  }

  @GetMapping("/{optionId}")
  public ResponseEntity<OptionDto> getOption(@PathVariable Long productId, @PathVariable Long optionId) {
    OptionDto option = optionService.getOptionById(optionId);
    return ResponseEntity.ok(option);
  }

  @PutMapping("/{optionId}")
  public ResponseEntity<OptionDto> updateOption(@PathVariable Long productId, @PathVariable Long optionId, @Valid @RequestBody OptionDto optionDto) {
    optionDto.setId(optionId);
    OptionDto updatedOption = optionService.updateOption(optionDto);
    return ResponseEntity.ok(updatedOption);
  }

  @DeleteMapping("/{optionId}")
  public ResponseEntity<Void> deleteOption(@PathVariable Long productId, @PathVariable Long optionId) {
    optionService.deleteOption(optionId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/check")
  public ResponseEntity<Boolean> checkOptionExists(@PathVariable Long productId, @RequestParam String name) {
    boolean exists = optionService.optionExistsByProductIdAndName(productId, name);
    return ResponseEntity.ok(exists);
  }
}