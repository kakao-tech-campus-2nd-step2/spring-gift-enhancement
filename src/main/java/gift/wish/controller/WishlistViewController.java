package gift.wish.controller;

import gift.product.domain.Product;
import gift.product.domain.ProductDTO;
import gift.wish.domain.WishlistDTO;
import gift.wish.domain.WishlistItem;
import gift.product.service.ProductService;
import gift.user.service.UserService;
import gift.wish.service.WishlistService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/wishlist")
public class WishlistViewController {
    private UserService userService;
    private ProductService productService;
    private WishlistService wishlistService;

    public WishlistViewController(UserService userService, ProductService productService,
        WishlistService wishlistService) {
        this.userService = userService;
        this.productService = productService;
        this.wishlistService = wishlistService;
    }

    @GetMapping("{id}")
    public String showWishlist(@PathVariable("id") Long userId, Model model,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<WishlistItem> wishlists = wishlistService.getWishlistByUserId(userId, pageable);
        model.addAttribute("wishlists", wishlists);
        model.addAttribute("id", userId);
        return "wishlist";
    }

    @GetMapping("{id}/new")
    public String showAddProduct(@PathVariable("id") Long userId, Model model,
                                @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDTO> productPages = productService.getAllProducts(pageable);
        model.addAttribute("products", productPages);
        model.addAttribute("userId", userId);
        return "add_wishlist";
    }
    @PostMapping("{id}/save")
    public String saveWishlist(@PathVariable("id") Long userId, @RequestBody List<WishlistDTO> wishlistDTOList) {
        List<WishlistItem> wishlistItemList = new ArrayList<>();
        for(WishlistDTO wishlistDTO : wishlistDTOList){
            WishlistItem wishlistItem = new WishlistItem();
            wishlistItem.setUser(userService.findById(userId).get());

            Long productId = wishlistDTO.getProductId();
            wishlistItem.setProduct(productService.getProductById(productId).get());

            wishlistItem.setAmount(wishlistDTO.getAmount());
            wishlistItemList.add(wishlistItem);
        }
        wishlistService.saveWishlistItemsWithUserId(userId, wishlistItemList);
        return "redirect:/wishlist/" + userId;
    }
}
