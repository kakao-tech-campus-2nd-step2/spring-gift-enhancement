package gift.controller;

import gift.dto.CategoryDTO;
import gift.model.Category;
import gift.service.CategoryService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getCategories(Model model) {
        List<Category> categories = categoryService.findAllCategories();
        model.addAttribute("categories", categories);
        return "category_list";
    }

    @GetMapping("/add")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("categoryDTO", new CategoryDTO(""));
        return "add_category_form";
    }

    @PostMapping
    public String addCategory(@ModelAttribute @Valid CategoryDTO categoryDTO,
        BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categoryDTO", categoryDTO);
            return "add_category_form";
        }
        Category existingCategory = categoryService.findCategoryByName(categoryDTO.name());
        if (existingCategory != null) {
            model.addAttribute("categoryError", "카테고리가 이미 존재합니다.");
            return "add_category_form";
        }
        categoryService.saveCategory(categoryDTO);
        return "redirect:/admin/categories";
    }

    @GetMapping("/{id}/edit")
    public String showEditCategoryForm(@PathVariable("id") long id, Model model) {
        Category category = categoryService.findCategoryById(id);
        model.addAttribute("categoryDTO", CategoryService.toDTO(category));
        model.addAttribute("categoryId", id);
        return "edit_category_form";
    }

    @PutMapping("/{id}")
    public String editCategory(@PathVariable("id") long id,
        @ModelAttribute @Valid CategoryDTO categoryDTO,
        BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categoryId", id);
            return "edit_category_form";
        }
        Category existingCategory = categoryService.findCategoryByName(categoryDTO.name());
        if (existingCategory != null) {
            model.addAttribute("categoryId", id);
            model.addAttribute("categoryError", "카테고리가 이미 존재합니다.");
            return "edit_category_form";
        }
        categoryService.updateCategory(categoryDTO, id);
        return "redirect:/admin/categories";
    }

    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable("id") long id) {
        categoryService.deleteCategory(id);
        return "redirect:/admin/categories";
    }

}
