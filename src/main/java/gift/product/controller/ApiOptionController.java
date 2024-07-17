package gift.product.controller;

import gift.product.dto.OptionDTO;
import gift.product.model.Option;
import gift.product.service.OptionService;
import gift.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ApiOptionController {

    private final OptionService optionService;
    private final ProductService productService;

    @Autowired
    public ApiOptionController(
        OptionService optionService,
        ProductService productService
    ) {
        this.optionService = optionService;
        this.productService = productService;
    }

    @GetMapping("/{productId}")
    public Page<Option> getAllOptions(@PathVariable Long productId, Pageable pageable) {
        System.out.println("[ApiOptionController] getAllOptions()");
        return optionService.getAllOptions(productId, pageable);
    }

    @PostMapping("/{productId}")
    public ResponseEntity<String> registerOption(@PathVariable Long productId, @RequestBody OptionDTO optionDTO) {
        System.out.println("[ApiOptionController] getAllOptions()");
        optionService.registerOption(
            new OptionDTO(
                optionDTO.getName(),
                optionDTO.getQuantity(),
                productId
            )
        );
        return ResponseEntity.status(HttpStatus.CREATED).body("Option registered successfully");
    }

    @PutMapping("/option/{id}")
    public ResponseEntity<String> updateOption(@PathVariable Long id, @RequestBody OptionDTO optionDTO) {
        System.out.println("[ApiOptionController] getAllOptions()");
        optionService.updateOption(
            new OptionDTO(
                id,
                optionDTO.getName(),
                optionDTO.getQuantity(),
                optionDTO.getProductId()
            )
        );
        return ResponseEntity.status(HttpStatus.CREATED).body("Option update successfully");
    }

}
