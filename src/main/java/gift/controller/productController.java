package gift.controller;

import gift.dto.ProductRequestDto;
import gift.dto.ProductResponseDto;
import gift.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/product/jdbc")
@RestController()
public class productController {

    private final ProductService productService;

    public productController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("")
    public ProductResponseDto createProductDto(@RequestBody ProductRequestDto productRequestDto) {
        return productService.createProductDto(productRequestDto.getName(), productRequestDto.getPrice(), productRequestDto.getUrl(), productRequestDto.getCategory());
    }

    @GetMapping("")
    public List<ProductResponseDto> getAll() {
        return productService.getAll().stream().map(ProductResponseDto::fromEntity).toList();
    }

    @GetMapping("/{id}")
    public ProductResponseDto getOneById(@PathVariable("id") Long id) {
        return productService.getOneById(id);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") Long id, @RequestBody ProductRequestDto productRequestDto) {
        productService.update(id, productRequestDto.getName(), productRequestDto.getPrice(), productRequestDto.getUrl(), productRequestDto.getCategory());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        productService.delete(id);
    }

    @GetMapping("/{name}")
    public ProductResponseDto getOneByName(@PathVariable("name") String name) {
        return ProductResponseDto.fromEntity(productService.findProductByName(name));
    }

    @GetMapping("/products")
    public Page<ProductResponseDto> getProducts(Pageable pageable) {
        return productService.getProducts(pageable);
    }
}
