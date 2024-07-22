package gift.domain.option;

import gift.domain.option.dto.OptionRequestDTO;
import gift.domain.option.dto.OptionResponseDTO;
import gift.global.response.ResponseMaker;
import gift.global.response.ResultResponseDto;
import gift.global.response.SimpleResultResponseDto;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OptionRestController {

    private final OptionService optionService;

    public OptionRestController(OptionService optionService) {
        this.optionService = optionService;
    }

    /**
     * 모든 옵션 목록 조회
     */
    @GetMapping("/options")
    public ResponseEntity<ResultResponseDto<List<Option>>> getOptions() {
        List<Option> options = optionService.getOptions();
        return ResponseMaker.createResponse(HttpStatus.OK, "모든 옵션 조회 성공", options);
    }

    /**
     * 특정 상품의 옵션 목록 조회
     */
    @GetMapping("/products/{productId}/options")
    public ResponseEntity<ResultResponseDto<OptionResponseDTO>> getOptionsByProductId(
        @PathVariable("productId") Long productId) {
        List<Option> options = optionService.getOptionsByProductId(productId);
        OptionResponseDTO optionResponseDTO = new OptionResponseDTO(options);

        return ResponseMaker.createResponse(HttpStatus.OK, "해당 상품의 옵션 조회 성공", optionResponseDTO);
    }

    /**
     * 특정 상품에 옵션 추가
     */
    @PostMapping("/products/{productId}/options")
    public ResponseEntity<SimpleResultResponseDto> addOption(
        @PathVariable("productId") Long productId,
        OptionRequestDTO optionRequestDTO) {
        optionService.addOption(productId, optionRequestDTO);

        return ResponseMaker.createSimpleResponse(HttpStatus.OK, "해당 상품에 옵션 추가 성공");
    }

    /**
     * 특정 상품의 옵션 수정
     */
    @PutMapping("/products/{productId}/options/{optionId}")
    public ResponseEntity<SimpleResultResponseDto> updateOption(
        @PathVariable("productId") Long productId,
        @PathVariable("optionId") Long optionId,
        @Valid @RequestBody OptionRequestDTO optionRequestDTO) {
        optionService.updateOption(productId, optionId, optionRequestDTO);

        return ResponseMaker.createSimpleResponse(HttpStatus.OK, "해당 상품에 옵션 수정 성공");
    }

}
