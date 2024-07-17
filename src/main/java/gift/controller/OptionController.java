package gift.controller;

import gift.service.OptionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("options")
public class OptionController {

    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @PostMapping("/delete/{id}")
    public String deleteOption(@PathVariable("id") Long optionId){
        optionService.deleteOneOption(optionId);
        return "redirect:/products";
    }
}
