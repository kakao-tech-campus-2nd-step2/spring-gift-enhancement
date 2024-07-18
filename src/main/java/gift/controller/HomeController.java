package gift.controller;

import gift.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class HomeController {

    private final ProductService productService;

    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/home")
    public String showHomeForm() {
        return "home";
    }


    @GetMapping("/products")
    public String showProductsPage() {
        return "products";
    }

    @GetMapping("/productlist")
    public String showProductListsPage() {
        return "user-products";
    }

    @GetMapping("/wishlist")
    public String wishlistForm() {
        return "wishlist";
    }


}
