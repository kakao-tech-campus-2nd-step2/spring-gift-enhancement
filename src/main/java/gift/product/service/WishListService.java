package gift.product.service;

import gift.product.dto.ProductDTO;
import gift.product.model.Product;
import gift.product.model.Wish;
import gift.product.repository.WishListRepository;
import gift.product.util.JwtUtil;
import gift.product.validation.WishListValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WishListService {
    private final WishListRepository wishListRepository;
    private final JwtUtil jwtUtil;
    private final WishListValidation wishListValidation;

    @Autowired
    public WishListService(
            WishListRepository wishListRepository,
            JwtUtil jwtUtil,
            WishListValidation wishListValidation
    ) {
        this.wishListRepository = wishListRepository;
        this.jwtUtil = jwtUtil;
        this.wishListValidation = wishListValidation;
    }

    public Page<ProductDTO> getAllProducts(String authorization, Pageable pageable) {
        System.out.println("[WishListService] getAllProducts()");
        Long memberId = jwtUtil.identification(authorization).getId();
        return  convertWishToProductDTOList(
                wishListRepository.findAllByMemberId(memberId, pageable),
                pageable
        );
    }

    public void registerWishProduct(String authorization, Map<String, Long> requestBody) {
        System.out.println("[WishListService] registerWishProduct()");
        Wish wish = wishListValidation.registerValidation(authorization, requestBody.get("productId"));
        wishListRepository.save(wish);
    }

    public void deleteWishProduct(String authorization, Long id) {
        System.out.println("[WishListService] deleteWishProduct()");
        wishListValidation.deleteValidation(authorization, id);
        wishListRepository.deleteById(id);
    }

    public Page<ProductDTO> convertWishToProductDTOList(Page<Wish> wishList, Pageable pageable) {
        List<ProductDTO> productDTOs = wishList.stream()
            .map(this::convertWishToProductDTO)
            .collect(Collectors.toList());
        return new PageImpl<>(
            productDTOs,
            pageable,
            wishList.getTotalElements()
        );
    }

    public ProductDTO convertWishToProductDTO(Wish wish) {
        Product product = wish.getProduct();
        return new ProductDTO(
                product.getName(),
                product.getPrice(),
                product.getImageUrl(),
                product.getCategory().getId()
        );
    }

}
