package gift.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gift.dto.OptionDto;
import gift.dto.response.OptionResponse;
import gift.service.OptionService;
import jakarta.validation.Valid;

import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/products")
public class OptionController{

    private final OptionService optionService;

    public OptionController(OptionService optionService){
        this.optionService = optionService;
    }
    
    @GetMapping("/{id}/options")
    public ResponseEntity<OptionResponse> getOptions(@PathVariable Long id){
        OptionResponse optionResponse = optionService.findByProductId(id);
        return new ResponseEntity<>(optionResponse, HttpStatus.OK);
    }

    @PostMapping("/{id}/otions/new")
    public ResponseEntity<?> addOption(@Valid @RequestBody OptionDto optionDto, BindingResult bindingResult, @PathVariable Long id){
        
        if(bindingResult.hasErrors()){
            Map<String, String> erros = new HashMap<>();
            for(FieldError error : bindingResult.getFieldErrors()){
                erros.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(erros, HttpStatus.BAD_REQUEST);
        }
        optionService.addOption(optionDto, id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
