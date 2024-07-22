package gift.controller;

import gift.dto.ProductDto;

import gift.dto.OptionDto;
import gift.entity.Category;
import gift.entity.Product;
import gift.service.CategoryService;
import gift.service.OptionService;

import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/api/products")
public class ProductController {

    private final CategoryService categoryService;
    private final ProductService productService;
    private OptionService optionService;


    @Autowired
    public ProductController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }



    @PostMapping("/add")
    public String addProduct(@Valid @ModelAttribute("product") ProductDto productDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("product", new ProductDto());

            model.addAttribute("categories", categoryService.getAllCategories());
            return "add-product";
        }
        productService.addProduct(productDto);
        return "redirect:/view/products";
    }



    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable Long id, @Valid @ModelAttribute("product") ProductDto productDto, BindingResult result, Model model) {

        Product product = productService.getProductById(productDto.getId());
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("options", optionService.getAllOptions());
            model.addAttribute("product", new ProductDto(product));

            return "edit-product";
        }
        if (productDto.getName().contains("카카오")) {
            result.rejectValue("name", "error.product", "상품 이름에 '카카오'가 포함되어 있습니다. 담당 MD와 협의하십시오.");
            List<Category> categories = categoryService.getAllCategories();
            model.addAttribute("categories", categories);
            return "edit-product";
        }
        productService.updateProduct(id, productDto);
        return "redirect:/view/products";
    }


}
