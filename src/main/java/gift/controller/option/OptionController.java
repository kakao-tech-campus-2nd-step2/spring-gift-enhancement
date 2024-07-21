package gift.controller.option;

import gift.dto.option.OptionRequest;
import gift.dto.option.OptionResponse;
import gift.service.option.OptionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/gifts")
public class OptionController {

    private OptionService optionService;

    @Autowired
    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @GetMapping("/options")
    public ResponseEntity<List<OptionResponse>> getAllOptions() {
        List<OptionResponse> options = optionService.getAllOptions();
        return ResponseEntity.ok(options);
    }

    @GetMapping("/{id}/options")
    public ResponseEntity<List<OptionResponse>> getAllOptionsFromGift(@PathVariable Long id) {
        List<OptionResponse> options = optionService.getOptionsByGiftId(id);
        return ResponseEntity.ok(options);
    }

    @PostMapping("/options/{id}")
    public ResponseEntity<String> addOptionToGift(@PathVariable("id") Long giftId,
                                                  @Valid @RequestBody OptionRequest optionRequest) {
        optionService.addOptionToGift(giftId, optionRequest);
        return ResponseEntity.ok("옵션이 상품에 추가되었습니다!");
    }

    @PutMapping("/options/{giftId}/{optionId}")
    public ResponseEntity<String> updateOptionToGift(@PathVariable("giftId") Long giftId,
                                                     @PathVariable("optionId") Long optionId,
                                                     @Valid @RequestBody OptionRequest optionRequest) {
        optionService.updateOptionToGift(giftId, optionId, optionRequest);
        return ResponseEntity.ok(giftId + "번 상품에서" + optionId + "번 옵션이 변경되었습니다!");
    }

    @PatchMapping("/options/{giftId}/{optionId}")
    public ResponseEntity<String> substractOptionToGift(@PathVariable("giftId") Long giftId,
                                                        @PathVariable("optionId") Long optionId,
                                                        @RequestParam(name = "quantity") int quantity) {
        optionService.subtractOptionToGift(giftId, optionId, quantity);
        return ResponseEntity.ok(giftId + "번 상품에서" + optionId + "번 옵션 수량이" + quantity + "만큼 차감되었습니다!");
    }

    @DeleteMapping("/options/{giftId}/{optionId}")
    public ResponseEntity<String> deleteOptionFromGift(@PathVariable("giftId") Long giftId,
                                                       @PathVariable("optionId") Long optionId) {
        optionService.deleteOptionFromGift(giftId, optionId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(giftId + "번 상품에서" + optionId + "번 옵션이 삭제되었습니다!");
    }
}
