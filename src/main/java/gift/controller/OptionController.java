package gift.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gift.dto.OptionDto;
import gift.dto.response.OptionResponse;
import gift.service.OptionService;

@RestController
@RequestMapping("/api/products")
public class OptionController{

    private final OptionService optionService;

    public OptionController(OptionService optionService){
        this.optionService = optionService;
    }
    
    @GetMapping("/{id}/options")
    public ResponseEntity<OptionResponse> getOptions(@PathVariable Long productId){
        OptionResponse optionResponse = optionService.findByProductId(productId);
        return new ResponseEntity<>(optionResponse, HttpStatus.OK);
    }

    @PostMapping("/{id}/otions/new")
    public ResponseEntity<Void> addOption(@RequestBody OptionDto optionDto, @PathVariable Long productId){
        optionService.addOption(optionDto, productId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
