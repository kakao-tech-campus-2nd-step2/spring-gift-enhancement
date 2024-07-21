package gift.controller;

import gift.dto.option.OptionQuantityDTO;
import gift.dto.option.SaveOptionDTO;
import gift.dto.option.UpdateOptionDTO;
import gift.service.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class OptionController {
    @Autowired
    OptionService optionService;

    @PostMapping("/api/option")
    @ResponseStatus(HttpStatus.CREATED)
    public void addOption(@RequestBody SaveOptionDTO saveOptionDTO){
        optionService.add(saveOptionDTO);
    }
    @DeleteMapping("/api/option/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void deleteOption(@PathVariable int id){
        optionService.delete(id);
    }
    @PutMapping("/api/option")
    @ResponseStatus(HttpStatus.OK)
    public void updateOption(@RequestBody UpdateOptionDTO updateOptionDTO){
        optionService.update(updateOptionDTO);
    }

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
