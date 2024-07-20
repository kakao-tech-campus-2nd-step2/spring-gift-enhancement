package gift.controller;

import gift.dto.ProductDto;
import gift.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductViewController {

  @Autowired
  private ProductService productService;

  @GetMapping("/new")
  public String showNewProductForm(Model model) {
    model.addAttribute("productDto", new ProductDto());
    return "product-form";
  }

  @PostMapping
  public String createProduct(@Valid @ModelAttribute ProductDto productDto, BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      return "product-form";
    }

    productService.createProduct(productDto);
    return "redirect:/products";
  }

  @GetMapping("/{id}/edit")
  public String showEditProductForm(@PathVariable Long id, Model model) {
    ProductDto productDto = productService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
    model.addAttribute("productDto", productDto);
    return "product-form";
  }

  @PostMapping("/{id}")
  public String updateProduct(@PathVariable Long id, @Valid @ModelAttribute ProductDto productDto, BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      return "product-form";
    }
    ProductDto updatedProductDto = new ProductDto(id, productDto.getName(), productDto.getPrice(), productDto.getImageUrl(), productDto.getCategoryId());
    productService.updateProduct(id, updatedProductDto);

    return "redirect:/products";
  }
}
