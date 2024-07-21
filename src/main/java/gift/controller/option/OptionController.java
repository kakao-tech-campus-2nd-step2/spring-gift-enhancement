package gift.controller.option;

import gift.config.LoginMember;
import gift.controller.auth.AuthController;
import gift.controller.auth.LoginResponse;
import gift.service.OptionService;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OptionController {

    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @GetMapping("/options")
    public ResponseEntity<Page<OptionResponse>> getAllOptions(
        @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(optionService.findAll(pageable));
    }

    @GetMapping("/products/{productId}/options")
    public ResponseEntity<Page<OptionResponse>> getAllOptionsByProductId(
        @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
        @PathVariable UUID productId) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK)
            .body(optionService.findAllByProductId(productId, pageable));
    }

    @GetMapping("/options/{optionId}")
    public ResponseEntity<OptionResponse> getOption(@PathVariable UUID optionId) {
        return ResponseEntity.status(HttpStatus.OK).body(optionService.find(optionId));
    }

    @PostMapping("/options/{productId}")
    public ResponseEntity<OptionResponse> createOption(@RequestBody OptionRequest option,
        @PathVariable UUID productId) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(optionService.save(productId, option));
    }

    @PutMapping("/options/{optionId}")
    public ResponseEntity<OptionResponse> updateOption(@LoginMember LoginResponse loginMember,
        @PathVariable UUID optionId, @RequestBody OptionRequest option) {
        AuthController.validateAdmin(loginMember);
        return ResponseEntity.status(HttpStatus.OK).body(optionService.update(optionId, option));
    }

    @PutMapping("/options/{optionId}/subtract/{quantity}")
    public ResponseEntity<OptionResponse> subtractOption(@PathVariable UUID optionId, @PathVariable Integer quantity) {
//        AuthController.validateAdmin(loginMember);
        return ResponseEntity.status(HttpStatus.OK)
            .body(optionService.subtract(optionId, quantity));
    }

    @DeleteMapping("/{optionId}")
    public ResponseEntity<Void> deleteOption(@LoginMember LoginResponse loginMember,
        @PathVariable UUID optionId) {
        AuthController.validateAdmin(loginMember);
        optionService.delete(optionId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}