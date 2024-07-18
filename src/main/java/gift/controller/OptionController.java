package gift.controller;

import gift.DTO.Option.OptionRequest;
import gift.DTO.Option.OptionResponse;
import gift.service.OptionService;
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
    public ResponseEntity<List<OptionResponse>> getOption(@PathVariable("product_id") Long product_id){
        List<OptionResponse> oneProductOption = optionService.findOneProductOption(product_id);

        return new ResponseEntity<>(oneProductOption, HttpStatus.OK);
    }

    @PostMapping("/api/products/{product_id}/options")
    public ResponseEntity<Void> createOption(
            @PathVariable("product_id") Long product_id, @RequestBody OptionRequest optionRequest
    ){
        optionService.save(product_id, optionRequest);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/api/products/{product_id}/options/{option_id}")
    public ResponseEntity<Void> updateOption(
            @PathVariable("option_id") Long option_id,
            @RequestBody OptionRequest optionRequest
    ){
        optionService.update(option_id, optionRequest);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/api/products/{product_id}/options/{option_id}")
    public ResponseEntity<Void> deleteOption(
            @PathVariable("option_id") Long option_id
    ){
        optionService.delete(option_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
