package gift.controller;

import gift.service.OptionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class OptionApiController {

    private final OptionService optionService;

    public OptionApiController(OptionService optionService) {
        this.optionService = optionService;
    }
}
