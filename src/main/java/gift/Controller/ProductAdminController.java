package gift.Controller;

import gift.DTO.CategoryDto;
import gift.DTO.ProductDto;
import gift.Service.CategoryService;
import gift.Service.ProductService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("admin/products")
public class ProductAdminController {

  private final ProductService productService;
  private final CategoryService categoryService;

  public ProductAdminController(ProductService productService, CategoryService categoryService) {
    this.productService = productService;
    this.categoryService = categoryService;
  }

  @GetMapping
  public String listProducts(Model model, Pageable pageable) {
    model.addAttribute("products", productService.getAllProducts(pageable));
    return "product-list";
  }

  @GetMapping("/new")
  public String newProductForm(Model model) {
    model.addAttribute("categories", categoryService.getAllCategories());
    model.addAttribute("product", new ProductDto());
    return "product-form";
  }

  @PostMapping("/add")
  public String addProduct(@RequestParam String name, @RequestParam int price,
    @RequestParam String imageUrl, @RequestParam Long categoryId) {
    CategoryDto categoryDto = categoryService.getCategoryById(categoryId);
    ProductDto productDto = new ProductDto(name, price, imageUrl, categoryDto);
    productService.addProduct(productDto);
    return "redirect:/admin/products";
  }

  @GetMapping("product/{id}")
  public String editProductForm(@PathVariable Long id, Model model) {
    ProductDto productDto = productService.getProductById(id);
    model.addAttribute("categories", categoryService.getAllCategories());
    model.addAttribute("product", productDto);
    return "product-form";
  }

  @PostMapping("product/{id}")
  public String updateProduct(@PathVariable Long id, @RequestParam String name,
    @RequestParam int price, @RequestParam String imageUrl, @RequestParam Long categoryId) {
    CategoryDto categoryDto = categoryService.getCategoryById(categoryId);
    ProductDto productDto = new ProductDto(name, price, imageUrl, categoryDto);

    productService.updateProduct(id, productDto);
    return "redirect:/admin/products";
  }

  @PostMapping("/{id}")
  public String deleteProduct(@PathVariable Long id) {
    productService.deleteProduct(id);
    return "redirect:/admin/products";
  }
}
