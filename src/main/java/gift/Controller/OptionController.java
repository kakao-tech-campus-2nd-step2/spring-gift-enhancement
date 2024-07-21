package gift.Controller;

import gift.Model.Option;
import gift.Service.OptionService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OptionController {
    private final OptionService optionService;

    public OptionController(OptionService optionService){
        this.optionService = optionService;
    }

    @GetMapping("/api/products/{productId}/options")
    public ResponseEntity<List<Option>> getOptions(@PathVariable(name = "productId") Long productId){
        return ResponseEntity.ok().body(optionService.getAllOptions(productId));
    }

    @PostMapping("/api/products/{productId}/options")
    public ResponseEntity<Option> addOption(@RequestBody Option option, @PathVariable(name = "productId") Long productId){
        return ResponseEntity.ok().body(optionService.addOption(option, productId));
    }

    @PutMapping("/api/products/{productId}/options/{optionId}")
    public ResponseEntity<Option> updateOption(@RequestBody Option option, @PathVariable(name = "productId") Long productId, @PathVariable(value = "optionId") Long optionId){
        return ResponseEntity.ok().body(optionService.updateOption(option,productId,optionId));
    }

    @DeleteMapping("/api/products/{productId}/options/{optionId}")
    public ResponseEntity<Option> deleteOption(@PathVariable(value = "productId") Long productId, @PathVariable(value = "optionId") Long optionId){
        return ResponseEntity.ok().body(optionService.deleteOption(productId,optionId));
    }
}
