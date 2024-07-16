package gift.wishlist;

import gift.member.MemberTokenResolver;
import gift.product.Product;
import gift.token.MemberTokenDTO;
import java.util.List;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WishlistPageController {

    private static final Logger log = LoggerFactory.getLogger(WishlistPageController.class);
    private final WishlistService wishlistService;

    public WishlistPageController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping("/wishlist")
    public String wishlist() {
        return "/wishlist/emptyWishlistPage";
    }

    @GetMapping("/wishlistPage")
    public String wishlistPage(
        @MemberTokenResolver MemberTokenDTO token,
        Model model,
        Pageable pageable
    ) {
        pageable = changePageable(pageable);
        Page<Product> products = wishlistService.getAllWishlists(token, pageable);

        model.addAttribute("products", products);
        model.addAttribute("page", pageable.getPageNumber() + 1);
        model.addAttribute("totalProductsSize", products.getTotalElements());
        model.addAttribute("currentPageProductSize", products.get().toList().size());
        model.addAttribute("pageLists",
            IntStream.range(1, products.getTotalPages() + 1).boxed().toList());

        return "/wishlist/page";
    }

    private Pageable changePageable(Pageable pageable) {
        List<Order> orders = pageable.getSort()
            .stream()
            .map(this::changeReferenceOfOrder)
            .toList();

        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(orders));
    }

    private Order changeReferenceOfOrder(Order order) {
        return new Order(order.getDirection(), "product." + order.getProperty());
    }
}
