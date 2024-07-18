package gift.controller.admin;

import gift.controller.dto.request.OptionRequest;
import gift.controller.dto.response.OptionResponse;
import gift.service.OptionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/option")
public class AdminOptionController {
    private final OptionService optionService;

    public AdminOptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @GetMapping("/new")
    public String newOption(Model model,
            @RequestParam("productId") @NotNull @Min(1) Long ProductId) {
        model.addAttribute("productId", ProductId);
        return "option/newOption";
    }

    @GetMapping("/{id}")
    public String editOption(Model model,
            @PathVariable("id") @NotNull @Min(1) Long id
    ) {
        OptionResponse option = optionService.findByIdFetchJoin(id);
        model.addAttribute("option", option);
        return "option/editOption";
    }

    @PostMapping("")
    public String createOption(@Valid @ModelAttribute OptionRequest.Create request) {
        optionService.save(request);
        return "redirect:/admin/product/" + request.productId();
    }

    @PutMapping("")
    public String updateOption(@Valid @ModelAttribute OptionRequest.Update request) {
        optionService.updateById(request);
        return "redirect:/admin/product/" + request.productId();
    }

    @DeleteMapping("/{id}/{productId}")
    public ResponseEntity<Void> deleteOptionById(
            @PathVariable("productId") @NotNull @Min(1) Long productId,
            @PathVariable("id") @NotNull @Min(1) Long id) {
        optionService.deleteByIdAndProductId(id, productId);
        return ResponseEntity.ok().build();
    }
}
