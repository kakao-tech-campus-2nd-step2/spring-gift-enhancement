package gift.Controller;

import gift.Model.Option;
import gift.Model.RequestOption;
import gift.Model.ResponseOptionDTO;
import gift.Service.OptionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/products/{product-id}/options")
public class OptionController {
    private final OptionService optionService;

    public OptionController(OptionService optionService){
        this.optionService = optionService;
    }

    @PostMapping
    public ResponseEntity<String> addOption (@PathVariable("product-id") Long productId, @Valid @RequestBody RequestOption requestOption){
        Option option = optionService.addOption(productId, requestOption);
        return ResponseEntity.created(URI.create("/api/products/"+productId+"/options/"+ option.getId())).body("옵션이 정상적으로 추가되었습니다");
    }

    @GetMapping
    public ResponseEntity<List<ResponseOptionDTO>> getOptions (@PathVariable("product-id") Long productId){
        List<ResponseOptionDTO> optionList = optionService.getOptions(productId);
        return new ResponseEntity<>(optionList, HttpStatus.OK);
    }

    @PutMapping("/{option-id}")
    public ResponseEntity<String> editOption(@PathVariable("product-id") Long productId, @PathVariable("option-id") Long optionId, @Valid @RequestBody RequestOption requestOption){
        optionService.editOption(productId, optionId, requestOption);
        return ResponseEntity.ok("옵션이 정상적으로 수정되었습니다");
    }

    @DeleteMapping("/{option-id}")
    public ResponseEntity<String> deleteOption(@PathVariable("product-id") Long productId, @PathVariable("option-id") Long optionId) {
        optionService.deleteOption(productId, optionId);
        return ResponseEntity.ok("옵션이 정상적으로 삭제되었습니다");
    }
}
