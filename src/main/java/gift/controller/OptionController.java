package gift.controller;

import gift.DTO.Option.OptionRequest;
import gift.DTO.Option.OptionResponse;
import gift.service.OptionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class OptionController {
    private final OptionService optionService;

    public OptionController(OptionService optionService){
        this.optionService = optionService;
    }

    @GetMapping("/api/products/{product_id}/options")
    public ResponseEntity<Page<OptionResponse>> readOption(
            @PathVariable("product_id") Long product_id,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "sort", defaultValue = "asc") String sort,
            @RequestParam(value = "field", defaultValue = "id") String field
    ){
        if(sort.equals("asc")){
            Page<OptionResponse> oneProductOption = optionService.findOptionASC(product_id, page, size, field);
            return new ResponseEntity<>(oneProductOption, HttpStatus.OK);
        }
        Page<OptionResponse> oneProductOption = optionService.findOptionDESC(product_id, page, size, field);
        return new ResponseEntity<>(oneProductOption, HttpStatus.OK);
    }

    @PostMapping("/api/products/{product_id}/options")
    public ResponseEntity<Void> createOption(
            @PathVariable("product_id") Long product_id, @Valid @RequestBody OptionRequest optionRequest
    ){
        optionService.save(product_id, optionRequest);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/api/products/{product_id}/options/{option_id}")
    public ResponseEntity<Void> updateOption(
            @PathVariable("option_id") Long option_id,
            @Valid @RequestBody OptionRequest optionRequest
    ){
        optionService.update(option_id, optionRequest);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/api/products/{product_id}/options/{option_id}")
    public ResponseEntity<Void> deleteOption(
            @PathVariable("product_id") Long productId,
            @PathVariable("option_id") Long optionId
    ){
        optionService.delete(productId, optionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
