package gift.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
 * 관리자 페이지 기본 화면 연결을 위한 Controller
 */
@Controller
public class PageController {
    @GetMapping("/")
    public String main() {
        return "index.html";
    }

    @GetMapping("/login")
    public String login(){
        return "login.html";
    }

    @GetMapping("/register")
    public String register(){
        return "register.html";
    }

    @GetMapping("/wishList")
    public String wishList(){
        return "wishList.html";
    }

    @GetMapping("/adminProducts")
    public String manageProduct(){
        return "adminProduct.html";
    }

    @GetMapping("/adminUsers")
    public String manageUser(){
        return "adminUser.html";
    }

    @GetMapping("/adminCategories")
    public String manageCategory(){
        return "adminCategory.html";
    }

    @GetMapping("/adminOptions")
    public String manageOption(@RequestParam Long product_id, Model model){
        model.addAttribute("product_id", product_id);
        return "adminOption.html";
    }
}
