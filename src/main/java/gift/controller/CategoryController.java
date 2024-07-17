package gift.controller;

import gift.domain.model.entity.Category;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
@Validated
public class CategoryController {

//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public List<Category> getCategories(){
//
//    }
}
