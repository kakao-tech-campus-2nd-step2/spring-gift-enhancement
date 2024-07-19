package gift.product.presentation.restcontroller;

import gift.product.business.service.OptionService;
import gift.product.presentation.dto.RequestOptionsDto;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/options")
public class OptionController {

    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @PostMapping
    public ResponseEntity<List<Long>> createOption(RequestOptionsDto requestOptionsDto) {
        var optionRegisterDtos = requestOptionsDto.toOptionRegisterDtos();
        var optionIds = optionService.createOption(optionRegisterDtos, requestOptionsDto.productId());
        return ResponseEntity.ok(optionIds);
    }
}
