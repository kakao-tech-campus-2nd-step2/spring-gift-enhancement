package gift.controller;

import gift.dto.OptionRequestDto;
import gift.dto.OptionResponseDto;
import gift.service.CategoryService;
import gift.service.OptionService;
import gift.service.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/products")
public class ProductApiController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final OptionService optionService;

    @Autowired
    public ProductApiController(ProductService productService,CategoryService categoryService,OptionService optionService){
        this.productService = productService;
        this.categoryService = categoryService;
        this.optionService = optionService;
    }
    // 옵션 추가
    @PostMapping("/{id}/options")
    public ResponseEntity<Void> addOption(@PathVariable Long id,
        @RequestBody OptionRequestDto optionRequestDto) {
        optionService.save(id, optionRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    // 옵션 확인
    @GetMapping("/{id}/options")
    public ResponseEntity<List<OptionResponseDto>> findAllOptions(@PathVariable Long id) {
        List<OptionResponseDto> options = optionService.findAll(id);
        return ResponseEntity.status(HttpStatus.OK).body(options);
    }

    // 옵션 삭제
    @DeleteMapping("/{id}/options")
    public ResponseEntity<Void> deleteOptions(@PathVariable Long id){
        optionService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
