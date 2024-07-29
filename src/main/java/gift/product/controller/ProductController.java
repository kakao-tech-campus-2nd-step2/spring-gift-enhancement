package gift.product.controller;

import gift.product.dto.OptionDto;
import gift.product.dto.ProductDto;
import gift.product.dto.ProductSortField;
import gift.product.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

  private final ProductService productService;

  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public ResponseEntity<Page<ProductDto>> getAllProducts(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "ID") ProductSortField sort,
      @RequestParam(defaultValue = "ASC") Sort.Direction direction) {

    Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort.getFieldName()));
    Page<ProductDto> products = productService.findAll(pageable);
    return ResponseEntity.ok(products);
  }


  @GetMapping("/{id}")
  public ResponseEntity<ProductDto> getProductById(@PathVariable long id) {
    ProductDto product = productService.getProductById(id);
    return ResponseEntity.ok(product);
  }


  @PostMapping
  public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
    ProductDto createdProduct = productService.addProduct(productDto);
    return ResponseEntity.ok(createdProduct);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductDto> updateProduct(@PathVariable long id,
      @Valid @RequestBody ProductDto productDto) {
    ProductDto updatedProduct = productService.updateProduct(id, productDto);
    return ResponseEntity.ok(updatedProduct);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteProduct(@PathVariable long id) {
    productService.deleteProduct(id);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{id}/options")
  public ResponseEntity<List<OptionDto>> getProductOptions(@PathVariable long id) {
    List<OptionDto> options = productService.getProductOptions(id);
    return ResponseEntity.ok(options);
  }
}
