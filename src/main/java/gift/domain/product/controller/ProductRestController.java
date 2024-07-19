package gift.domain.product.controller;

import gift.domain.product.dto.ProductDetailResponseDto;
import gift.domain.product.dto.ProductRequestDto;
import gift.domain.product.dto.ProductResponseDto;
import gift.domain.product.service.ProductService;
import jakarta.validation.Valid;
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
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductDetailResponseDto> create(@RequestBody @Valid ProductRequestDto productRequestDto) {
        ProductDetailResponseDto productDetailResponseDto = productService.create(productRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDetailResponseDto);
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponseDto>> readAll(Pageable pageable) {
        Page<ProductResponseDto> productResponseDtos = productService.readAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(productResponseDtos);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailResponseDto> readById(@PathVariable("productId") long productId) {
        ProductDetailResponseDto productDetailResponseDto = productService.readById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(productDetailResponseDto);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDetailResponseDto> update(@PathVariable("productId") long productId, @RequestBody @Valid ProductRequestDto productRequestDto) {
        ProductDetailResponseDto productDetailResponseDto = productService.update(productId, productRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(productDetailResponseDto);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> delete(@PathVariable("productId") long productId) {
        productService.delete(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
