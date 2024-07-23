package gift.controller;

import gift.dto.OptionQuantityRequest;
import gift.dto.OptionRequest;
import gift.dto.ResponseMessage;
import gift.entity.Option;
import gift.service.OptionService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/{productId}/options")
    public ResponseEntity<Option> addOption(@PathVariable("productId") Long productId,
        @Valid @RequestBody OptionRequest request) {
        Option option = optionService.addOption(productId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(option);
    }

    @GetMapping("/options")
    public List<Option> getAllOptions() {
        return optionService.getAllOptions();
    }

    @GetMapping("/{productId}/options")
    public List<Option> getAllOptionsByProductId(@PathVariable("productId") Long productId) {
        return optionService.getAllOptionsByProductId(productId);
    }

    @GetMapping("/options/{id}")
    public Option getOptionById(@PathVariable("id") Long id) {
        return optionService.getOneOptionById(id);
    }

    @PutMapping("/{productId}/options/{id}")
    public ResponseEntity<Option> updateOption(@PathVariable("productId") Long productId,
        @PathVariable("id") Long id, @Valid @RequestBody OptionRequest request) {
        Option option = optionService.updateOption(productId, id, request);
        return ResponseEntity.ok(option);
    }

    @DeleteMapping("/{productId}/options/{id}")
    public ResponseEntity<ResponseMessage> deleteOption(@PathVariable("productId") Long productId,
        @PathVariable("id") Long id) {
        optionService.deleteOption(productId, id);
        ResponseMessage responseMessage = new ResponseMessage("삭제되었습니다.");
        return ResponseEntity.ok(responseMessage);
    }

    @PutMapping("/{productId}/options/{id}/sub")
    public ResponseEntity<Option> subtractOptionQuantity(@PathVariable("productId") Long productId,
        @PathVariable("id") Long id, @RequestBody OptionQuantityRequest request) {
        Option option = optionService.subtractOptionQuantity(productId, id, request);
        return ResponseEntity.ok(option);
    }

}
