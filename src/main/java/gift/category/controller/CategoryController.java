package gift.category.controller;

import gift.category.model.dto.CreateCategoryRequest;
import gift.user.model.dto.AppUser;
import gift.user.resolver.LoginUser;
import gift.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category/admin")
public class CategoryController {
    private final UserService userService;

    public CategoryController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> addProductForAdmin(@LoginUser AppUser loginAppUser,
                                                     @Valid @RequestBody CreateCategoryRequest createCategoryRequest) {
        userService.verifyAdminAccess(loginAppUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("ok");
    }
}
