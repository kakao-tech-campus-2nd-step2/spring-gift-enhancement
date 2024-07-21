package gift.controller;

import gift.dto.OptionDTO;
import gift.service.OptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products/{productId}/options")
public class OptionController {
    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @GetMapping
    public ResponseEntity<List<OptionDTO>> getOptionsByProductId(@PathVariable Long productId) {
        List<OptionDTO> options = optionService.getOptionsByProductId(productId);
        return new ResponseEntity<>(options, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OptionDTO> addOptionToProduct(@PathVariable Long productId, @RequestBody OptionDTO optionDTO) {
        OptionDTO createdOption = optionService.addOptionToProduct(productId, optionDTO);
        return new ResponseEntity<>(createdOption, HttpStatus.CREATED);
    }
}