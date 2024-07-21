//package gift.option.controller;
//
//import gift.option.dto.OptionDto;
//import gift.option.service.OptionService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//
//@RestController
//@RequestMapping("/api/products")
//public class OptionController {
//
//    @Autowired
//    private OptionService optionService;
//
//    @GetMapping("/{productId}/options")
//    public List<OptionDto> getOptionsByProductId(@PathVariable("productId") Long productId) {
//        return optionService.getOptionsByProductId(productId);
//    }
//
//    @PostMapping("/{productId}/options")
//    public OptionDto addOptionToProduct(@PathVariable("productId") Long productId, @RequestBody OptionDto optionDto) {
//        return optionService.addOptionToProduct(productId, optionDto);
//    }
//
//}