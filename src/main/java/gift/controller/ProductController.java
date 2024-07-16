package gift.controller;

import gift.domain.ProductDTO;
import gift.service.ProductService;
import gift.service.ProductServiceStatus;
import jakarta.validation.constraints.Min;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    private ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 상품 추가(Create)
    @PostMapping
    public ResponseEntity<ProductServiceStatus> addProduct(@RequestBody ProductDTO productDTO) {
        ProductServiceStatus response = productService.createProduct(productDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 단일 상품 조회(Read)
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> selectProduct(@PathVariable Long id) {
        Optional<ProductDTO> productDTO = productService.getProduct(id);
        return productDTO
            .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 전체 상품 조회(Read)
    @GetMapping
    public ResponseEntity<Page<ProductDTO>> selectAllProducts(
        @Min(value = 1, message = "페이지 번호는 1이상이어야 합니다.")
        @RequestParam(value = "page", defaultValue = "0") int page,

        @Min(value = 1, message = "한 페이지당 1개 이상의 항목이 포함되어야 합니다.")
        @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDTO> products = productService.getAllProducts(pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // 상품 수정(Update)
    @PutMapping("/{id}")
    public ProductServiceStatus updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        return productService.editProduct(id, productDTO);
    }

    // 상품 삭제(Delete)
    @DeleteMapping("/{id}")
    public ProductServiceStatus deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }
}