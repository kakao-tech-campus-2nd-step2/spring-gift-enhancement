package gift.controller;

import gift.dto.ProductDto;
import gift.dto.ProductResponseDto;
import gift.dto.ProductUpdateDto;
import gift.service.ProductService;
import gift.vo.Product;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    /**
     * 상품 조회 -전체 (페이징)
     * 페이지 번호와 페이지 크기를 사용하여 페이징된 상품 목록을 조회한다.
     * @param pageNumber 요청 시 페이지 번호 지정, 기본값 0
     * @param pageSize 요청 시 페이지 크기 지정, 기본값 10
     * @return 페이징된 상품 목록, 관련 메타데이터 포함한 응답 객체 반환
     */
    @GetMapping()
    public ResponseEntity<Map<String, Object>> getAllProducts(
            @RequestParam(defaultValue = "1") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize) {
        Page<Product> allProductsPaged = service.getAllProducts(pageNumber-1, pageSize);

        List<ProductResponseDto> productResponseDtos = allProductsPaged.getContent().stream()
                .map(ProductResponseDto::toProductResponseDto)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("content", productResponseDtos);
        response.put("totalPages", allProductsPaged.getTotalPages());
        response.put("currentPageNumber", allProductsPaged.getNumber());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 상품 조회 - 한 개
     * @param id 조회할 상품의 ID
     * @return 조회한 product
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable(value = "id") Long id) {
        Product product = service.getProductById(id);
        ProductResponseDto productResponseDto = ProductResponseDto.toProductResponseDto(product);

        return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
    }

    /**
     * 상품 추가
     * @param productDto Dto로 받음
     * @return ResponseEntity로 Response
     */
    @PostMapping()
    public ResponseEntity<Void> addProduct(@Valid @RequestBody ProductDto productDto) {
        service.addProduct(productDto);
        return ResponseEntity.noContent().build();
    }

    /**
     * 상품 수정
     * @param productUpdateDto 수정할 상품 (JSON 형식)
     *    ㄴ받는 Product에 id 필드 값이 포함 되어 있어야 한다.
     * @return 수정된 상품
     */
    @PutMapping()
    public ResponseEntity<Void> updateProduct(@Valid @RequestBody ProductUpdateDto productUpdateDto) {
        service.updateProduct(productUpdateDto);
        return ResponseEntity.noContent().build();
    }

    /**
     * 상품 삭제
     * @param id 삭제할 상품의 ID
     * @return HTTP State - No Content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(value = "id") Long id) {
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
