package gift.controller;

import static gift.util.ResponseEntityUtil.responseError;

import gift.constants.ResponseMsgConstants;
import gift.dto.CategoryDTO;
import gift.dto.ResponseDTO;
import gift.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Validated
@RequestMapping("/api/categories")
public class CategoryController {

    CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<?> getCategories() {
        try {
            return new ResponseEntity<>(categoryService.getCategories(), HttpStatus.OK);
        } catch (RuntimeException e) {
            return responseError(e);
        }
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addCategory(@Validated @RequestBody CategoryDTO categoryDTO) {
        try {
            categoryService.addCategory(categoryDTO);
            return new ResponseEntity<>(new ResponseDTO(false, ResponseMsgConstants.WELL_DONE_MESSAGE), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return responseError(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updateCategory(@PathVariable Long id, @Validated @RequestBody CategoryDTO categoryDTO) {
        try{
            categoryService.updateCategory(id, categoryDTO);
            return new ResponseEntity<>(new ResponseDTO(false, ResponseMsgConstants.WELL_DONE_MESSAGE), HttpStatus.OK);
        } catch (RuntimeException e) {
            return responseError(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteCategory(@PathVariable Long id) {
        try{
            categoryService.deleteCategory(id);
            return new ResponseEntity<>(new ResponseDTO(false, ResponseMsgConstants.WELL_DONE_MESSAGE), HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return responseError(e);
        }
    }
}
