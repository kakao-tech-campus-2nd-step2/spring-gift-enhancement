package gift.option.controller;

import gift.option.domain.OptionRequest;
import gift.option.service.OptionService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/options")
public class OptionController {
    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    // 옵션 등록 처리
    @PostMapping
    public String createOption(@ModelAttribute @Valid OptionRequest optionRequest) {
        optionService.createOption(optionRequest.getProductId(), optionRequest);
        return "redirect:/api/products/" + optionRequest.getProductId() + "/options";
    }
}
