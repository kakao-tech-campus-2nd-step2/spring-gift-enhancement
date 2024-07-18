package gift.domain.option;

import gift.global.response.ResponseMaker;
import gift.global.response.ResultResponseDto;
import gift.global.response.SimpleResultResponseDto;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OptionController {

    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @GetMapping("/options")
    public ResponseEntity<ResultResponseDto<List<Option>>> getOptions() {
        List<Option> options = optionService.getOptions();
        return ResponseMaker.createResponse(HttpStatus.OK, "모든 옵션 조회 성공", options);
    }

    @GetMapping("/products/{productId}/options")
    public ResponseEntity<ResultResponseDto<List<Option>>> getOptionsByProductId(
        @PathVariable("productId") Long productId) {
        List<Option> options = optionService.getOptionsByProductId(productId);
        return ResponseMaker.createResponse(HttpStatus.OK, "해당 상품의 옵션 조회 성공", options);
    }

    @DeleteMapping("/options/{optionId}")
    public ResponseEntity<SimpleResultResponseDto> deleteOption(
        @PathVariable("optionId") Long optionId) {
        optionService.deleteById(optionId);
        return ResponseMaker.createSimpleResponse(HttpStatus.OK, "옵션 삭제 성공");
    }
}
