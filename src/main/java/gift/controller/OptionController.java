package gift.controller;

import gift.dto.OptionDto;
import gift.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products/{productId}/options")
public class OptionController {

  private final OptionService optionService;

  @Autowired
  public OptionController(OptionService optionService) {
    this.optionService = optionService;
  }

  @GetMapping
  public ResponseEntity<List<OptionDto>> getOptionsByProductId(@PathVariable Long productId) {
    List<OptionDto> options = optionService.getOptionsByProductId(productId);
    return ResponseEntity.ok(options);
  }

  @PostMapping
  public ResponseEntity<OptionDto> addOptionToProduct(@PathVariable Long productId, @RequestBody OptionDto optionDto) {
    OptionDto createdOption = optionService.addOptionToProduct(productId, optionDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdOption);
  }
}
