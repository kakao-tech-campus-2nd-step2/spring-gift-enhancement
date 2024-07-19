package gift.product;

import gift.Exception.ErrorResponse;
import gift.option.Option;
import gift.option.OptionRequest;
import gift.option.OptionService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductService productService;
    private final OptionService optionService;

    public ProductController(ProductRepository productRepository, ProductService productService, OptionService optionService){
        this.productRepository = productRepository;
        this.productService = productService;
        this.optionService = optionService;
    }

    @GetMapping()
    public String listProducts(Model model) {
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("newProduct", new Product()); // 새 상품 객체
        model.addAttribute("product", new Product()); // 편집을 위한 빈 객체*/
        return "admin"; // Thymeleaf 템플릿 이름
    }

    @PostMapping("/post")
    public ResponseEntity<HttpStatus> createProduct(@Valid @ModelAttribute ProductRequest newProduct) {
        return ResponseEntity.ok(productService.createProduct(newProduct.toEntity()));
    }

    @PutMapping("/update")
    public ResponseEntity<HttpStatus> updateProduct(@Valid @ModelAttribute ProductRequest changeProduct)
        throws NotFoundException {
        return ResponseEntity.ok(productService.updateProduct(changeProduct.toEntity()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.deleteProduct(id));
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(400).body(new ErrorResponse(ex.getMessage()));
    }

    @RequestMapping("/{id}/options")
    public class ProductOptionController {

        @GetMapping()
        public ResponseEntity<List<Option>> getOptions(@PathVariable("id") Long productId){
            return ResponseEntity.status(HttpStatus.OK).body(optionService.findAllByProductId(productId));
        }

        @PostMapping()
        public ResponseEntity<List<Option>> addOption(@RequestBody OptionRequest optionRequest,
            @PathVariable Long id){
            Option option = optionService.addOption(optionRequest);

            return ResponseEntity.status(HttpStatus.CREATED).body(productService.addOption(id, option));
        }

        @PutMapping()
        public ResponseEntity<String> updateOption(@RequestBody OptionRequest optionRequest){
            optionService.updateOption(optionRequest);

            return ResponseEntity.status(HttpStatus.CREATED).body("update");
        }

        @DeleteMapping()
        public ResponseEntity<?> deleteOption(@PathVariable("id") Long productId, @RequestParam Long optionId){
            Option option = optionService.deleteOption(optionId);

            return ResponseEntity.status(HttpStatus.OK).body(productService.deleteOption(productId, option));
        }





    }

}