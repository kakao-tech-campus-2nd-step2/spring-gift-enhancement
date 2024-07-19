package gift.controller;

import gift.dto.OptionDTO;
import gift.entity.Option;
import gift.entity.Product;
import gift.service.OptionService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/option")
public class OptionController {

    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @PostMapping
    public ResponseEntity<String> addOption(@RequestBody OptionDTO optionDTO) {
        Product product = optionService.findProductById(optionDTO.getProductId());
        Option option = optionDTO.toEntity(product);
        optionService.addOption(option);

        return new ResponseEntity<>("Option 추가 완료", HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateOption(@PathVariable("id") Long id, @RequestBody OptionDTO optionDTO) {
        Product product = optionService.findProductById(optionDTO.getProductId());
        Option option = optionDTO.toEntity(product);

        optionService.updateOption(option,id);
        return new ResponseEntity<>("Option 수정 완료", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOption(@PathVariable("id") Long id) {
        optionService.deleteOption(id);
        return new ResponseEntity<>("Option 삭제 완료",HttpStatus.NO_CONTENT);
    }
}
