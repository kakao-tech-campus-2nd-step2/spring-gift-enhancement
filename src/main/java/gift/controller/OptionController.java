package gift.controller;


import gift.classes.RequestState.OptionRequestStateDTO;
import gift.classes.RequestState.RequestStateDTO;
import gift.classes.RequestState.RequestStatus;
import gift.dto.RequestOptionDto;
import gift.dto.OptionDto;
import gift.services.OptionService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class OptionController {

    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

//    제품 아이디로 Option 조회
    @GetMapping("/{productId}/options")
    public ResponseEntity<OptionRequestStateDTO> getOptionsByProductId(@PathVariable Long productId) {
        List<OptionDto> options = optionService.getOptionsByProductId(productId);
        return ResponseEntity.ok().body(new OptionRequestStateDTO(
            RequestStatus.success,
            null,
            options
        ));
    }

////    Option 추가
//    @PostMapping("/{productId}/options")
//    public ResponseEntity<RequestStateDTO> addOption(@PathVariable Long productId, @Valid @RequestBody
//    RequestOptionDto requestOptionDto) {
//        optionService.addOption(productId, requestOptionDto);
//        return ResponseEntity.ok().body(new RequestStateDTO(
//            RequestStatus.success,
//            null
//        ));
//    }

//    Option 수정
    @PutMapping("/{productId}/options/{optionId}")
    public ResponseEntity<RequestStateDTO> updateOption(@PathVariable Long optionId, @Valid @RequestBody
    RequestOptionDto requestOptionDto) {
        optionService.updateOption(optionId, requestOptionDto);
        return ResponseEntity.ok().body(new RequestStateDTO(
            RequestStatus.success,
            null
        ));
    }

//    Option 삭제
    @DeleteMapping@PutMapping("/{productId}/options/{optionId}")
    public void deleteOption(@PathVariable Long optionId) {
        optionService.deleteOption(optionId);
    }

}
