package gift.main.controller;

import gift.main.dto.OptionRequest;
import gift.main.dto.OptionResponse;
import gift.main.service.OptionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * *옵션리스트는 처음 프로덕트 등록시에만 사용한다.
 *
 * 1. 옵션 수정
 * 2. 옵션 추가
 * 3. 옵션 삭제 (최소 하나 이상 있어야한다.)
 *
 * */

@RestController
@RequestMapping("/admin/product")
public class OptionController {

    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @GetMapping("/{id}/options")
    public ResponseEntity<?> findOptionAll(@PathVariable(value = "id") long productId) {
        List<OptionResponse> options = optionService.findOptionAll(productId);
        return ResponseEntity.ok(options);
    }

    @DeleteMapping("/{productId}/options/{optionId}")
    public ResponseEntity<?> deleteOption(@PathVariable(value = "productId") long productId,
                                          @PathVariable(value = "productId") long optionId) {
        optionService.deleteOption(productId, optionId);
        return ResponseEntity.ok("Option deleted successfully");
    }

    @PostMapping("/{productId}/options")
    public ResponseEntity<?> addOption(@PathVariable(value = "productId") long productId,
                                       @PathVariable(value = "productId") long optionId,
                                       @Valid @RequestBody OptionRequest optionRequest) {
        optionService.addOption(productId, optionRequest);
        return ResponseEntity.ok("Option added successfully");
    }

    @PutMapping("/{productId}/options/{optionId}")
    public ResponseEntity<?> updateOption(@PathVariable(value = "productId") long productId,
                                          @PathVariable(value = "productId") long optionId,
                                          @Valid @RequestBody OptionRequest optionRequest) {
        optionService.updateOption(productId, optionId, optionRequest);
        return ResponseEntity.ok("Option updated successfully");
    }
}
