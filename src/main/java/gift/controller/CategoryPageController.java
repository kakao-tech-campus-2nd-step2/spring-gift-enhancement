package gift.controller;

import gift.dto.CategoryDTO;
import gift.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class CategoryPageController {

    private final CategoryService categoryService;

    public CategoryPageController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String viewCategoryPage(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "category";
    }

    @GetMapping("/new")
    public String createCategoryForm(Model model) {
        CategoryDTO categoryDTO = new CategoryDTO();
        model.addAttribute("category", categoryDTO);
        return "addCategory";
    }

    @PostMapping("/save")
    public String createCategory(@Valid @ModelAttribute("category") CategoryDTO categoryDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "addCategory";
        }
        categoryService.createCategory(categoryDTO);
        return "redirect:/categories";
    }

    @GetMapping("/update/{id}")
    public String updateCategoryForm(@PathVariable long id, Model model) {
        CategoryDTO categoryDTO = categoryService.getCategoryById(id);
        model.addAttribute("category", categoryDTO);
        return "editCategory";
    }

    @PostMapping("/update/{id}")
    public String updateCategory(@PathVariable long id, @Valid @ModelAttribute("category") CategoryDTO categoryDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "editCategory";
        }
        categoryService.updateCategory(id, categoryDTO);
        return "redirect:/categories";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable long id) {
        categoryService.deleteCategoryById(id);
        return "redirect:/categories";
    }
}
