package gift.controller;

import gift.domain.Category;
import gift.domain.Category.CreateCategory;
import gift.domain.Category.DetailCategory;
import gift.domain.Category.SimpleCategory;
import gift.domain.Category.UpdateCategory;
import gift.service.CategoryService;
import gift.util.page.PageMapper;
import gift.util.page.PageResult;
import gift.util.page.SingleResult;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public PageResult<SimpleCategory> getCategoryList(@Valid Category.getList req) {
        return PageMapper.toPageResult(categoryService.getCategoryList(req));
    }

    @GetMapping("/{id}")
    public SingleResult<DetailCategory> getCategory(@PathVariable long id) {
        return new SingleResult(categoryService.getCategory(id));
    }

    @PostMapping
    public SingleResult<Long> createCategory(@Valid @RequestBody CreateCategory create) {
        return new SingleResult(categoryService.createCategory(create));
    }

    @PutMapping("/{id}")
    public SingleResult<Long> updateCategory(@PathVariable long id, @Valid @RequestBody UpdateCategory update) {
        return new SingleResult(categoryService.updateCategory(id,update));
    }

    @DeleteMapping("/{id}")
    public SingleResult<Long> deleteCategory(@PathVariable long id) {
        return new SingleResult(categoryService.deleteCategory(id));
    }

}
