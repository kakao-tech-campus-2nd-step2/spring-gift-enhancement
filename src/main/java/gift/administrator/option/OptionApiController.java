package gift.administrator.option;

import gift.administrator.product.Product;
import gift.administrator.product.ProductDTO;
import gift.administrator.product.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
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
@RequestMapping("/api")
public class OptionApiController {

    private final OptionService optionService;
    private final ProductService productService;

    public OptionApiController(OptionService optionService, ProductService productService) {
        this.optionService = optionService;
        this.productService = productService;
    }

    @GetMapping("/products/{productId}/options")
    public ResponseEntity<List<OptionDTO>> getAllOptionsByProductId(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(optionService.getAllOptionsByProductId(productId));
    }

    @GetMapping("/options")
    public ResponseEntity<List<OptionDTO>> getAllOptions() {
        return ResponseEntity.ok(optionService.getAllOptions());
    }

    @PostMapping("/products/{productId}/options")
    public ResponseEntity<List<OptionDTO>> addOptionsByProductId(@PathVariable("productId") Long productId,
        @Valid @RequestBody List<OptionDTO> options)
        throws NotFoundException {
        ProductDTO productDTO = productService.getProductById(productId);
        Product product = productDTO.toProduct(productDTO,
            productService.getCategoryById(productDTO.getCategoryId()));
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(optionService.addOptionsByProductId(productId, options, product));
    }

    @PutMapping("/options/{optionId}")
    public ResponseEntity<OptionDTO> updateOptionByOptionId(
        @PathVariable("productId") Long optionId, @Valid @RequestBody OptionDTO optionDTO)
        throws NotFoundException {
        ProductDTO productDTO = productService.getProductById(optionDTO.getProductId());
        Product product = productDTO.toProduct(productDTO,
            productService.getCategoryById(productDTO.getCategoryId()));
        return ResponseEntity.status(HttpStatus.OK)
            .body(optionService.updateOptionByOptionId(optionId, optionDTO, product));
    }

    @DeleteMapping("/options/{optionId}")
    public ResponseEntity<OptionDTO> deleteOptionByOptionId(@PathVariable("productId") Long optionId)
        throws NotFoundException {
        optionService.deleteOptionByOptionId(optionId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
