package gift.Controller;

import gift.DTO.OptionDto;
import gift.Service.OptionService;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api")
public class OptionController {

  private final OptionService optionService;

  public OptionController(OptionService optionService) {
    this.optionService = optionService;
  }


  @PostMapping("/options")
  public ResponseEntity<OptionDto> addOption(@RequestBody OptionDto optionDto) {
    OptionDto addedOptionDto = optionService.addOption(optionDto);

    var location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
      .buildAndExpand(addedOptionDto).toUri();

    return ResponseEntity.created(location).body(addedOptionDto);
  }

  @GetMapping("/options")
  public ResponseEntity<List<OptionDto>> getAllOptions() {
    List<OptionDto> optionDtos = optionService.getAllOptions();

    return ResponseEntity.ok(optionDtos);
  }

  @GetMapping("products/{id}/options")
  public ResponseEntity<OptionDto> getOptionById(@PathVariable Long id) {
    OptionDto optionDto = optionService.getOptionById(id);

    return ResponseEntity.ok(optionDto);
  }

  @DeleteMapping("products/{id}/options")
  public ResponseEntity<OptionDto> deleteOption(@PathVariable Long id) {
    OptionDto optionDto = optionService.deleteOption(id);

    return ResponseEntity.ok(optionDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<OptionDto> updateOption(@PathVariable Long id,
    @RequestBody OptionDto optionDto) {
    OptionDto updatedOptionDto = optionService.updateOption(id, optionDto);

    return ResponseEntity.ok(updatedOptionDto);
  }
}
