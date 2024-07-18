package gift.controller;

import gift.dto.option.OptionQuantityDTO;
import gift.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class OptionController {
    @Autowired
    OptionService optionService;

    @PostMapping("/api/option/refill")
    @ResponseStatus(HttpStatus.OK)
    public void refill(@RequestBody OptionQuantityDTO optionQuantityDTO){
        optionService.refill(optionQuantityDTO);
    }

    @PostMapping("/api/option/order")
    @ResponseStatus(HttpStatus.OK)
    public void order(@RequestBody OptionQuantityDTO optionQuantityDTO){
        optionService.order(optionQuantityDTO);
    }
}
