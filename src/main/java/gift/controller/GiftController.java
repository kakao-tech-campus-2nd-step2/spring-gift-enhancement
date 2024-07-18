package gift.controller;

import gift.dto.PagingRequest;
import gift.dto.PagingResponse;
import gift.model.gift.GiftRequest;
import gift.model.gift.GiftResponse;
import gift.model.option.OptionRequest;
import gift.model.option.OptionResponse;
import gift.service.gift.GiftService;
import gift.service.option.OptionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gifts")
public class GiftController {

    private GiftService giftService;
    private OptionService optionService;

    @Autowired
    public GiftController(GiftService giftService, OptionService optionService) {
        this.giftService = giftService;
        this.optionService = optionService;
    }


    @PostMapping
    public ResponseEntity<String> addGift(@Valid @RequestBody GiftRequest giftRequest) {
        giftService.addGift(giftRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Gift created");
    }

    @GetMapping("/{id}")
    public ResponseEntity<GiftResponse> getGift(@PathVariable Long id) {
        GiftResponse gift = giftService.getGift(id);
        return ResponseEntity.ok(gift);
    }

    @GetMapping
    public ResponseEntity<PagingResponse<GiftResponse>> getAllGift(@ModelAttribute PagingRequest pagingRequest) {
        PagingResponse<GiftResponse> response = giftService.getAllGifts(pagingRequest.getPage(), pagingRequest.getSize());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/options")
    public ResponseEntity<List<OptionResponse>> getOptions(@PathVariable Long id) {
        List<OptionResponse> options = optionService.getOptionsByGiftId(id);
        return ResponseEntity.ok(options);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateGift(@PathVariable Long id, @RequestBody GiftRequest giftRequest) {
        giftService.updateGift(giftRequest, id);
        return ResponseEntity.ok("상품 수정이 완료되었습니다.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGift(@PathVariable Long id) {
        giftService.deleteGift(id);
        return ResponseEntity.status(200).body("삭제가 완료되었습니다");
    }
}