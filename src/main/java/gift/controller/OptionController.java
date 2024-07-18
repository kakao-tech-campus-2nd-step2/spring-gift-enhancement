package gift.controller;

import gift.model.Option;
import gift.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products/{productId}/options")
public class OptionController {

  @Autowired
  private OptionService optionService;

  @GetMapping
  public ResponseEntity<List<Option>> getOptionsByProductId(@PathVariable Long productId) {
    List<Option> options = optionService.getOptionsByProductId(productId);
    return ResponseEntity.ok(options);
  }

  @PostMapping
  public ResponseEntity<Option> addOptionToProduct(@PathVariable Long productId, @RequestBody Option option) {
    Option createdOption = optionService.addOptionToProduct(productId, option);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdOption);
  }
}
