package gift.controller;

import gift.controller.dto.OptionResponse;
import gift.controller.dto.PaginationDTO;
import gift.controller.dto.ProductRequest;
import gift.controller.dto.ProductResponse;
import gift.service.GiftService;
import gift.utils.PaginationUtils;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/products")
public class GiftAdminController {

    private final GiftService giftService;

    public GiftAdminController(GiftService giftService) {
        this.giftService = giftService;
    }


    @PostMapping
    public ResponseEntity<ProductResponse> postProduct(@Valid @RequestBody ProductRequest productRequest) {
        ProductResponse DTO = giftService.postProducts(productRequest);
        return new ResponseEntity<>(DTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id,
        @Valid @RequestBody ProductRequest productRequest) {
        ProductResponse DTO = giftService.putProducts(productRequest, id);
        return ResponseEntity.ok(DTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteProduct(@PathVariable Long id) {
        Long i = giftService.deleteProducts(id);
        return ResponseEntity.ok(i);
    }

}
