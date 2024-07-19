package gift.Controller;

import gift.Model.OptionDto;
import gift.Service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products/options")
public class OptionController {

    private final OptionService optionService;

    @Autowired
    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<List<OptionDto>> getAllOptionsByProductId(@PathVariable long productId) {
        List<OptionDto> optionDto = optionService.getAllOptionsByProductId(productId);
        return ResponseEntity.ok().body(optionDto);
    }

    @PostMapping("/{productId}/add")
    public ResponseEntity<?> addOption(@PathVariable long productId, @RequestBody OptionDto optionDto) {
        try {
            optionDto.setProductId(productId);
            optionService.addOption(optionDto);
            return ResponseEntity.ok().body("Option added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding option");
        }
    }

    @PutMapping("/{productId}/{optionId}/update")
    public ResponseEntity<?> updateOption(@PathVariable long productId, @PathVariable long optionId, @RequestBody OptionDto optionDto) {
        try {
            optionDto.setProductId(productId);
            optionDto.setId(optionId);
            optionService.updateOption(optionDto);
            return ResponseEntity.ok().body("Option updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating option");
        }
    }

    @DeleteMapping("/{productId}/{optionId}/delete")
    public ResponseEntity<?> deleteOption(@PathVariable long productId, @PathVariable long optionId) {
        try {
            optionService.deleteOption(optionId);
            return ResponseEntity.ok().body("Option deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting option");
        }
    }
}
