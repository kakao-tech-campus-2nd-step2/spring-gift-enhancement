package gift.administrator.option;

import java.util.List;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OptionApiController {

    private final OptionService optionService;

    public OptionApiController(OptionService optionService) {
        this.optionService = optionService;
    }

    @GetMapping("/products/{productId}/options")
    public ResponseEntity<List<OptionDTO>> getAllOptionsByProductId(
        @PathVariable("productId") Long productId) {
        return ResponseEntity.ok(optionService.getAllOptionsByProductId(productId));
    }

    @GetMapping("/options")
    public ResponseEntity<List<OptionDTO>> getAllOptions() {
        return ResponseEntity.ok(optionService.getAllOptions());
    }

    @DeleteMapping("/options/{optionId}")
    public ResponseEntity<OptionDTO> deleteOptionByOptionId(
        @PathVariable("optionId") Long optionId)
        throws NotFoundException {
        optionService.deleteOptionByOptionId(optionId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
