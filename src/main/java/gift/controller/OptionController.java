package gift.controller;

import gift.model.option.OptionRequest;
import gift.model.option.OptionResponse;
import gift.service.option.OptionService;
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
                                                  @RequestBody OptionRequest optionRequest) {
        optionService.addOption(giftId, optionRequest);
        return ResponseEntity.ok("옵션이 상품에 추가되었습니다!");

    }


}
