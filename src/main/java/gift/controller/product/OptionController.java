package gift.controller.product;

import gift.controller.product.dto.OptionRequest;
import gift.controller.product.dto.OptionResponse;
import gift.service.product.OptionService;
import gift.service.product.dto.OptionModel;
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

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @GetMapping("/products/{id}/options")
    public ResponseEntity<OptionResponse.InfoList> getOptions(
        @PathVariable("id") Long productId
    ) {
        List<OptionModel.Info> models = optionService.getOptions(productId);
        OptionResponse.InfoList response = OptionResponse.InfoList.from(models);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/products/{id}/options")
    public ResponseEntity<OptionResponse.InfoList> createOption(
        @PathVariable("id") Long productId,
        @RequestBody OptionRequest.Register request
    ) {
        List<OptionModel.Info> models = optionService.createOption(productId, request.toCommand());
        OptionResponse.InfoList response = OptionResponse.InfoList.from(models);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/products/{productId}/options/{optionId}")
    public ResponseEntity<OptionResponse.Info> updateOption(
        @PathVariable("optionId") Long optionId,
        @PathVariable("productId") Long productId,
        @RequestBody OptionRequest.Update request
    ) {
        OptionModel.Info model = optionService.updateOption(optionId, productId,
            request.toCommand());
        OptionResponse.Info response = OptionResponse.Info.from(model);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/products/{productId}/options/{optionId}")
    public ResponseEntity<String> deleteOption(
        @PathVariable("productId") Long productId,
        @PathVariable("optionId") Long optionId
    ) {
        optionService.deleteOption(productId, optionId);
        return ResponseEntity.ok("Deleted correctly");
    }
}
