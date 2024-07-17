package gift.feat.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import gift.feat.product.service.ProductService;
import gift.feat.user.service.UserService;
import gift.feat.wishProduct.Service.WishProductService;

@Controller
@RequestMapping("/user")
public class UserController {
	private final UserService userService;
	private final ProductService productService;
	private final WishProductService wishProductService;

	@Autowired
	public UserController(UserService userService, ProductService productService,
		WishProductService wishProductService) {
		this.userService = userService;
		this.productService = productService;
		this.wishProductService = wishProductService;
	}

	@GetMapping("/login")
	public String login() {
		return "user/login";
	}
}
