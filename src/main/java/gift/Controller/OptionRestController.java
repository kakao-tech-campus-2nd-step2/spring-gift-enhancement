package gift.Controller;

import gift.Model.DTO.OptionDTO;
import gift.Service.OptionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OptionRestController {
    private final OptionService optionService;

    public OptionRestController(OptionService optionService){
        this.optionService = optionService;
    }

    @GetMapping("/products/{id}/options")
    public List<OptionDTO> read(@PathVariable Long id){
        return optionService.read(id);
    }
}
