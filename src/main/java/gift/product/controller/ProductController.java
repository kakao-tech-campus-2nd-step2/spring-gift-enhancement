package gift.product.controller;

import gift.product.dto.ProductDto;
import gift.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public void addProduct(@Valid @RequestBody ProductDto productDto) {
        productService.save(productDto);
    }

    @PutMapping("/{productId}")
    public void editProduct(@PathVariable Long productId, @Valid @RequestBody ProductDto productDto) {
        productService.update(productId, productDto);
    }

    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        productService.deleteById(productId);
    }

    /** 페이지네이션을 위한 새로운 엔드포인트
     * 특정 페이지와 크기 요청: /api/products?page=1&size=5
     * 페이지 번호: 0
     * 페이지 크기: 10
     * 정렬: 이름을 기준으로 오름차순 정렬 (기본값) **/
    @GetMapping("/api/products")
    public Page<ProductDto> getProducts(
            @RequestParam(defaultValue = "0") int page, // 클라이언트가 특정 페이지를 요청할 때 이 파라미터를 사용
            @RequestParam(defaultValue = "10") int size, // 한 페이지에 몇 개의 항목이 표시될지를 정의
            @RequestParam(defaultValue = "name,asc") String[] sort) // 배열 형태. 순서대로 정렬 속성, 정렬 방향 표시
    {
        int maxSize = 50; // 최대 페이지 크기를 50으로 제한
        size = Math.min(size, maxSize); // 50 초과 입력시 50으로 설정

        String sortBy = sort[0]; // sort 배열의 첫 번째 요소는 정렬 기준
        Sort.Direction direction = Sort.Direction.fromString(sort[1]); // sort 배열의 두 번째 요소는 정렬 방향

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        return productService.findAll(pageable);
    }

    @GetMapping("/{productId}")
    public ProductDto getProductById(@PathVariable Long productId) {
        return productService.findById(productId);
    }
}