package gift.Controller;

import gift.DTO.OptionDto;
import gift.Service.OptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/options")
public class OptionController {
  private final OptionService optionService;

  public OptionController(OptionService optionService){
    this.optionService=optionService;
  }


  @PostMapping
  public ResponseEntity<OptionDto> addOption(@RequestBody OptionDto optionDto){
    OptionDto addedOptionDto = optionService.addOption(optionDto);

    var location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
      .buildAndExpand(addedOptionDto).toUri();

    return ResponseEntity.created(location).body(addedOptionDto);
  }
}
