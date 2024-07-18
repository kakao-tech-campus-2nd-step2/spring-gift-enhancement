package gift.controller.option;

import gift.dto.option.OptionRequest;
import gift.dto.option.OptionResponse;
import gift.service.option.OptionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/options")
public class OptionController {

    private OptionService optionService;

    @Autowired
    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @GetMapping
    public ResponseEntity<List<OptionResponse>> getAllOptions() {
        List<OptionResponse> options = optionService.getAllOptions();
        return ResponseEntity.ok(options);
    }

    @PostMapping("/gifts/{id}")
    public ResponseEntity<String> addOptionToGift(@PathVariable("id") Long giftId,
                                                  @Valid @RequestBody OptionRequest optionRequest) {
        optionService.addOptionToGift(giftId, optionRequest);
        return ResponseEntity.ok("옵션이 상품에 추가되었습니다!");
    }

    @PutMapping("/gifts/{giftId}/{optionId}")
    public ResponseEntity<String> updateOptionToGift(@PathVariable("giftId") Long giftId,
                                                     @PathVariable("optionId") Long optionId,
                                                     @Valid @RequestBody OptionRequest optionRequest) {
        optionService.updateOptionToGift(giftId, optionId, optionRequest);
        return ResponseEntity.ok(giftId + "번 상품에서" + optionId + "번 옵션이 변경되었습니다!");
    }

    @DeleteMapping("/gifts/{giftId}/{optionId}")
    public ResponseEntity<String> deleteOptionFromGift(@PathVariable("giftId") Long giftId,
                                                       @PathVariable("optionId") Long optionId) {
        optionService.deleteOptionFromGift(giftId, optionId);
        return ResponseEntity.ok(giftId + "번 상품에서" + optionId + "번 옵션이 삭제되었습니다!");
    }


}
