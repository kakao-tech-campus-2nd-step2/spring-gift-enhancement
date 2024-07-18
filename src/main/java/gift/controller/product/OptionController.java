package gift.controller.product;

import gift.controller.product.dto.OptionRequest;
import gift.controller.product.dto.OptionResponse;
import gift.service.product.OptionService;
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

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @GetMapping("/products/{id}/options")
    public ResponseEntity<OptionResponse.InfoList> getOptions(
        @PathVariable("id") Long productId
    ) {
        var models = optionService.getOptions(productId);
        var response = OptionResponse.InfoList.from(models);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/products/{id}/options")
    public ResponseEntity<OptionResponse.Info> createOption(
        @PathVariable("id") Long productId,
        @RequestBody OptionRequest.Register request
    ) {
        var models = optionService.createOption(productId, request.toCommand());
        var response = OptionResponse.Info.from(models);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/products/options/{optionId}")
    public ResponseEntity<OptionResponse.Info> updateOption(
        @PathVariable("optionId") Long optionId,
        @RequestBody OptionRequest.Update request
    ) {
        var model = optionService.updateOption(optionId, request.toCommand());
        var response = OptionResponse.Info.from(model);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/products/options/{optionId}")
    public ResponseEntity<String> deleteOption(
        @PathVariable("optionId") Long optionId
    ) {
        optionService.deleteOption(optionId);
        return ResponseEntity.ok().body("Deleted correctly");
    }
}
