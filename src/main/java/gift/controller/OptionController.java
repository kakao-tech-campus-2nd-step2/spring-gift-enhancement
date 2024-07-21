package gift.controller;


import gift.dto.OptionRequestDTO;
import gift.dto.OptionResponseDTO;
import gift.entity.Option;
import gift.repository.OptionRepository;
import gift.service.OptionService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OptionController {

    private final OptionService optionService;

    @Autowired
    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @GetMapping("options")
    public ResponseEntity<List<OptionResponseDTO>> getOptions() {
        List<OptionResponseDTO> options = optionService.findAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(options);
    }

    @GetMapping("options/{optionId}")
    public ResponseEntity<OptionResponseDTO> getOptionsByProductId(@PathVariable("optionId") Long optionId) {
        OptionResponseDTO option = optionService.getOption(optionId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(option);
    }

    @PostMapping("options")
    public ResponseEntity<String> createOption(@Valid @RequestBody OptionRequestDTO optionRequestDTO) {
        optionService.addOption(optionRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("상품 option 등록 완료");
    }

    @DeleteMapping("options/{optionId}")
    public ResponseEntity<String> deleteOption(@PathVariable("optionId") Long optionId) {
        optionService.removeOption(optionId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("option 삭제 완료");
    }

    @PutMapping("options/{optionId}")
    public ResponseEntity<String> updateOption(@PathVariable("optionId") Long optionId ,
                                               @Valid @RequestBody OptionRequestDTO optionRequestDTO) {
        optionService.updateOption(optionId, optionRequestDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body("상품 option update 완료");
    }

    @PutMapping("options/{optionId}/subtract/{quantity}")
    public ResponseEntity<String> subtractOption(@PathVariable("optionId") Long optionId ,
                                                 @PathVariable("quantity") int quantity){
        optionService.subtractOption(optionId, quantity);
        return ResponseEntity.status(HttpStatus.OK)
                .body("상품 option 빼기 완료");
    }
}
