package gift.controller;

import gift.dto.OptionDTO;
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

import java.util.List;

@RestController
@RequestMapping("/api/products/{productId}/options")
public class OptionController {

    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @GetMapping
    public ResponseEntity<List<OptionDTO>> getOptions(@PathVariable Long productId) {
        List<OptionDTO> options = optionService.getOptionsByProductId(productId);
        return new ResponseEntity<>(options, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OptionDTO> addOption(@PathVariable Long productId, @RequestBody OptionDTO optionDTO) {
        OptionDTO createdOption = optionService.addOption(productId, optionDTO);
        return new ResponseEntity<>(createdOption, HttpStatus.CREATED);
    }

    @DeleteMapping("/{optionId}")
    public ResponseEntity<Void> deleteOption(@PathVariable Long optionId) {
        optionService.deleteOption(optionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
