//package gift.admin;
//
//import gift.product.dto.ProductDto;
//
//import gift.product.model.Product;
//import gift.product.repository.ProductRepository;
//import gift.product.service.ProductService;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Optional;
//
//@Controller
//@RequestMapping("/admin")
//public class AdminController {
//    private final ProductService productService;
//    private final ProductRepository productRepository;
//
//    @Autowired
//    public AdminController(ProductService productService, ProductRepository productRepository) {
//        this.productService = productService;
//        this.productRepository = productRepository;
//    }
//
//    @GetMapping("/products/list")
//    public String listProducts(
//            @RequestParam(defaultValue = "0") int page, // 쿼리 파라미터 page를 받아 페이지 번호 설정, 기본값은 0
//            @RequestParam(defaultValue = "10") int size, // 쿼리 파라미터 size를 받아 페이지 크기 설정, 기본값은 10
//            @RequestParam(defaultValue = "name,asc") String[] sort, Model model) // 뷰에 데이터를 전달하기 위한 모델 객체
//    {
//        // page와 size의 최대값 설정
//        int maxPage = 100;
//        int maxSize = 50;
//
//        // 제한값 적용
//        int limitedPage = Math.min(page, maxPage);
//        int limitedSize = Math.min(size, maxSize);
//
//        Sort.Direction direction = Sort.Direction.fromString(sort[1]); // sort 배열의 두 번째 요소 정렬 방향을 Sort.Direction 객체로 변환
//        Pageable pageable = PageRequest.of(limitedPage, limitedSize, Sort.by(direction, sort[0])); // 제한된 페이지 번호와 페이지 크기, 정렬 기준 및 방향으로 Pageable 객체 생성
//
//        Page<Product> productPage = productRepository.findAll(pageable);
//        model.addAttribute("상품 목록", productPage.getClass()); // 현재 페이지의 상품 목록을 모델에 추가
//        model.addAttribute("현재 페이지", limitedPage); // 현재 페이지 번호를 모델에 추가
//        model.addAttribute("전체 페이지", productPage.getTotalPages()); // 총 페이지 수를 모델에 추가
//        model.addAttribute("전체 항목", productPage.getTotalElements()); // 총 항목 수를 모델에 추가
//
//        return "list"; // list.html 파일 보여주기
//    }
//
//    @GetMapping("/{productId}")
//    public String viewProduct(@PathVariable Long id, Model model) {
//        Optional<Product> product = productRepository.findById(id);
//        model.addAttribute("product", product);
//        return "view"; // view.html 파일 보여주기
//    }
//
//    @GetMapping("/new")
//    public String showAddProductForm(Model model) {
//        model.addAttribute("productDto", new ProductDto());
//        return "add"; // add.html 파일 보여주기
//    }
//
//    @PostMapping
//    public String addProduct(@Valid @ModelAttribute("productDto") ProductDto productDto, BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            return "add"; // 에러가 있으면 다시 add.html 보여주기
//        }
//        productService.save(productDto);
//        return "redirect:/admin/products";
//    }
//
//    @GetMapping("/{productId}/edit")
//    public String showEditProductForm(@PathVariable Long id, Model model) {
//        Optional<Product> product = productRepository.findById(id);
//        model.addAttribute("product", product); // 모델에 상품 추가
//        return "edit"; // 렌더링할 뷰의 이름 반환
//    }
//
//    @PostMapping("/{productId}/edit")
//    public String editProduct(@PathVariable Long id, @Valid @ModelAttribute("productDto") ProductDto productDto, BindingResult result) {
//        if (result.hasErrors()) {
//            return "edit"; // 에러가 있으면 다시 edit.html 보여주기
//        }
//        productService.update(id, productDto);
//        return "redirect:/admin/products/list";
//    }
//}