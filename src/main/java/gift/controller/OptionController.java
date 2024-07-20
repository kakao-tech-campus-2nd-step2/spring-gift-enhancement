package gift.controller;

import gift.dto.OptionDTO;
import gift.dto.OptionRequestDTO;
import gift.dto.OptionResponseDTO;
import gift.service.OptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RestController
@RequestMapping("/api/products/{productId}/options")
public class OptionController {

    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @PostMapping("/{optionId}/subtract")
    public ResponseEntity<Void> subtractOptionQuantity(@PathVariable Long productId, @PathVariable Long optionId, @RequestParam int quantity) {
        optionService.subtractOptionQuantity(productId, optionId, quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<OptionResponseDTO>> getOptions(@PathVariable Long productId) {
        List<OptionResponseDTO> options = optionService.getOptionsByProductId(productId);
        return new ResponseEntity<>(options, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OptionResponseDTO> addOption(@PathVariable Long productId, @RequestBody OptionRequestDTO optionRequestDTO) {
        OptionResponseDTO createdOption = optionService.addOption(productId, optionRequestDTO);
        return new ResponseEntity<>(createdOption, HttpStatus.CREATED);
    }

    @DeleteMapping("/{optionId}")
    public ResponseEntity<Void> deleteOption(@PathVariable Long optionId) {
        optionService.deleteOption(optionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
