package gift.users.wishlist;

import gift.administrator.option.Option;
import gift.administrator.option.OptionDTO;
import gift.administrator.option.OptionService;
import gift.users.user.User;
import gift.users.user.UserDTO;
import gift.util.JwtUtil;
import gift.administrator.product.Product;
import gift.administrator.product.ProductDTO;
import gift.administrator.product.ProductService;
import gift.users.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class WishListService {

    private final WishListRepository wishListRepository;
    private final ProductService productService;
    private final UserService userService;
    private final OptionService optionService;
    private final JwtUtil jwtUtil;

    public WishListService(WishListRepository wishListRepository, ProductService productService,
        UserService userService, OptionService optionService,
        JwtUtil jwtUtil) {
        this.wishListRepository = wishListRepository;
        this.productService = productService;
        this.userService = userService;
        this.optionService = optionService;
        this.jwtUtil = jwtUtil;
    }

    public Page<WishListDTO> getWishListsByUserId(long id, int page, int size, Direction direction,
        String sortBy) {
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageRequest = PageRequest.of(page, size, sort);
        Page<WishList> wishListPage = wishListRepository.findAllByUserId(id, pageRequest);
        List<WishListDTO> wishLists = wishListPage.stream()
            .map(WishListDTO::fromWishList)
            .toList();
        return new PageImpl<>(wishLists, pageRequest, wishListPage.getTotalElements());
    }

    public void extractEmailFromTokenAndValidate(HttpServletRequest request, String email) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        String token = authHeader.substring(7);
        String tokenEmail;
        tokenEmail = jwtUtil.extractEmail(token);
        if (!email.equals(tokenEmail)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "이메일이 토큰과 일치하지 않습니다.");
        }
    }

    public WishListDTO addWishList(WishListDTO wishListDTO, String email) throws NotFoundException {
        UserDTO userDTO = userService.findUserByEmail(email);
        User user = userDTO.toUser();
        if (wishListRepository.existsByUserIdAndProductId(user.getId(),
            wishListDTO.getProductId())) {
            throw new IllegalArgumentException(email + "의 위시리스트에 존재하는 상품입니다.");
        }
        validateOptionId(wishListDTO.getProductId(), wishListDTO.getOptionId());
        ProductDTO productDTO = productService.getProductById(wishListDTO.getProductId());
        Product product = productService.getProductById(wishListDTO.getProductId())
            .toProduct(productDTO,
                productService.getCategoryById(productDTO.getCategoryId()));
        product.setOption(optionService.getAllOptionsByOptionId(
                productDTO.getOptions().stream().map(OptionDTO::getId).toList()).stream()
            .map(optionDTO -> optionDTO.toOption(product)).toList());
        OptionDTO optionDTO = optionService.findOptionById(wishListDTO.getOptionId());
        Option option = optionDTO.toOption(product);
        WishList wishList = new WishList(user, product, wishListDTO.getNum(), option);
        user.addWishList(wishList);
        product.addWishList(wishList);
        wishListRepository.save(wishList);
        return WishListDTO.fromWishList(wishList);
    }

    public void validateOptionId(long productId, long optionId) {
        if (!optionService.existsByOptionIdAndProductId(optionId,
            productId)) {
            throw new IllegalArgumentException(
                optionId + " 옵션은 " + productId + " 상품에 존재하지 않는 옵션입니다.");
        }
    }

    public WishListDTO updateWishList(long userId, long productId, WishListDTO wishListDTO)
        throws NotFoundException {
        if (!wishListRepository.existsByUserIdAndProductId(userId, productId)) {
            throw new IllegalArgumentException(
                userService.findById(userId).email() + "의 위시리스트에는 " + productService.getProductById(
                    productId).getName()
                    + " 상품이 존재하지 않습니다.");
        }
        WishList wishList = wishListRepository.findByUserIdAndProductId(userId, productId);
        validateOptionId(productId, wishListDTO.getOptionId());
        ProductDTO productDTO = productService.getProductById(productId);
        Product product = productDTO.toProduct(productDTO,
            productService.getCategoryById(productDTO.getCategoryId()));
        List<OptionDTO> optionDTOList = optionService.getAllOptionsByOptionId(
            productDTO.getOptions().stream().map(OptionDTO::getId).toList());
        List<Option> options = optionDTOList.stream().map(optionDTO -> optionDTO.toOption(product))
            .toList();
        product.setOption(options);
        OptionDTO newOptionDTO = optionService.findOptionById(wishListDTO.getOptionId());
        Option newOption = newOptionDTO.toOption(product);
        wishList.setOption(newOption);
        wishList.setNum(wishListDTO.getNum());
        newOption.addWishList(wishList);
        wishListRepository.save(wishList);
        return WishListDTO.fromWishList(wishList);
    }

    public void deleteWishList(long userId, long productId) throws NotFoundException {
        if (!wishListRepository.existsByUserIdAndProductId(userId, productId)) {
            throw new IllegalArgumentException(
                userService.findById(userId).email() + "의 위시리스트에는 " + productService.getProductById(
                    productId).getName()
                    + " 상품이 존재하지 않습니다.");
        }
        WishList wishList = wishListRepository.findByUserIdAndProductId(userId, productId);
        wishList.getOption().removeWishList(wishList);
        wishList.getUser().removeWishList(wishList);
        wishList.getProduct().removeWishList(wishList);
        wishListRepository.deleteByUserIdAndProductId(userId, productId);
    }
}
