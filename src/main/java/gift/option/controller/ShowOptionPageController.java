package gift.option.controller;

import gift.option.domain.Option;
import gift.option.domain.OptionRequest;
import gift.option.service.OptionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/api/options")
public class ShowOptionPageController {
    private final OptionService optionService;

    public ShowOptionPageController(OptionService optionService) {
        this.optionService = optionService;
    }

    // 옵션 메인 페이지 반환
    @GetMapping
    public String showOptionForm(Model model) {
        List<Option> options = optionService.getAllOptions();
        model.addAttribute("options", options);
        return "Option/option";
    }

    // 옵션 등록 페이지 반환
    @GetMapping("/new")
    public String showOptionCreateForm(@RequestParam("productId") Long productId, Model model) {
        OptionRequest optionRequest = new OptionRequest();
        optionRequest.setProductId(productId);
        model.addAttribute("option", optionRequest);
        return "Option/create_option";
    }
}
