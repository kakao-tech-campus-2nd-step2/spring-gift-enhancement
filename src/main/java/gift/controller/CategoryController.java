package gift.controller;


import gift.dto.category.CategoryDTO;
import gift.entity.Category;
import gift.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/api/category")
    @ResponseBody
    public Page<CategoryDTO> getCategory(@RequestParam(value = "page", defaultValue = "0") int pageNum){
        Pageable pageable = PageRequest.of(pageNum, 2, Sort.by(Sort.Direction.ASC, "id"));
        return categoryService.getCategory(pageable);
    }

    @PostMapping("/api/category")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCategory(@RequestBody Category category) {
        categoryService.save(category);
    }

    @PutMapping("/api/category")
    @ResponseStatus(HttpStatus.OK)
    public void updateCategory(@RequestBody CategoryDTO categoryDTO) {
        categoryService.update(categoryDTO);
    }

    @DeleteMapping("/api/category/{id}")
    public String deleteCategory(@PathVariable("id") int id) {
        categoryService.delete(id);
        return "redirect:/api/category";
    }

}
