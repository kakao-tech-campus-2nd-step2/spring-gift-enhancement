package gift.controller;

import gift.dto.ProductRequestDto;
import gift.dto.ProductResponseDto;
import gift.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/product/jdbc")
@RestController()
public class productController {

    private final ProductService productService;

    public productController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("")
    public ProductResponseDto createProductDto(@RequestBody ProductRequestDto productRequestDto) {
        ProductResponseDto productResponseDto = productService.createProductDto(
                productRequestDto.getName(),
                productRequestDto.getPrice(),
                productRequestDto.getUrl(),
                productRequestDto.getCategory(),
                productRequestDto.getOptions()
        );
        productResponseDto.setHttpStatus(HttpStatus.OK);
        return productResponseDto;
    }

    @GetMapping("")
    public ProductResponseDto getAll() {
        ProductResponseDto productResponseDto = productService.getAllAndMakeProductResponseDto();
        productResponseDto.setHttpStatus(HttpStatus.OK);
        return productResponseDto;
    }

    @GetMapping("/{id}")
    public ProductResponseDto getOneById(@PathVariable("id") Long id) {
        return productService.getProductResponseDtoById(id);
    }

    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable("id") Long id, @RequestBody ProductRequestDto productRequestDto) {
        productService.update(id, productRequestDto.getName(), productRequestDto.getPrice(), productRequestDto.getUrl(), productRequestDto.getCategory(), productRequestDto.getOptions());
        return new ProductResponseDto(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        productService.delete(id);
    }

    @GetMapping("/{name}")
    public ProductResponseDto getOneByName(@PathVariable("name") String name) {
        return productService.fromEntity(productService.findProductByName(name));
    }

    @GetMapping("/products")
    public Page<ProductResponseDto> getProducts(Pageable pageable) {
        return productService.getProducts(pageable);
    }
}
