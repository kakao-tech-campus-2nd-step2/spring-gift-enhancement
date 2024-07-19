package gift.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import gift.Util.JWTUtil;
import gift.dto.product.ShowProductDTO;
import gift.entity.Product;
import gift.entity.User;

import gift.entity.WishList;
import gift.exception.exception.BadRequestException;
import gift.exception.exception.NotFoundException;
import gift.exception.exception.UnAuthException;
import gift.repository.ProductRepository;

import gift.repository.UserRepository;
import gift.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class WishListService {
    @Autowired
    WishListRepository wishListRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    public void add(String token, int productId) {
        int tokenUserId = jwtUtil.getUserIdFromToken(token);
        if(!jwtUtil.validateToken(token))
            throw new UnAuthException("로그인 만료");
        if(productRepository.findById(productId).isEmpty())
            throw new NotFoundException("해당 물건이없습니다.");
        if(userRepository.findById(tokenUserId).isEmpty())
            throw new UnAuthException("인증이 잘못되었습니다");

        Product product = productRepository.findById(productId).get();
        User user = userRepository.findById(tokenUserId).get();
        WishList wishList = new WishList(user,product);
        if(wishListRepository.findByUserAndProduct(user,product).isPresent())
            throw new BadRequestException("이미 추가된 물품입니다.");

        wishList.setProduct(product);
        wishList.setUser(user);
        wishListRepository.save(wishList);

    }

    public Page<ShowProductDTO> getWishList(String token, Pageable pageable) throws JsonProcessingException {
        int tokenUserId = jwtUtil.getUserIdFromToken(token);
        if(!jwtUtil.validateToken(token))
            throw new UnAuthException("로그인 만료");
        return wishListRepository.findByUserId(tokenUserId,pageable);

    }

    public void deleteWishList(String token, int productId) {
        if(!jwtUtil.validateToken(token))
            throw new UnAuthException("로그인 만료");
        if(productRepository.findById(productId).isEmpty())
            throw new NotFoundException("해당 물건이 없음");

        int tokenUserId = jwtUtil.getUserIdFromToken(token);
        Product product = productRepository.findById(productId).get();
        User user = userRepository.findById(tokenUserId).get();
        Optional<WishList> wishListOptional = wishListRepository.findByUserAndProduct(user,product);
        if(wishListOptional.isEmpty())
            throw new NotFoundException("위시리스트에 없음");
        WishList wishlist = wishListOptional.get();
        product.deleteWishlist(wishlist);
        user.deleteWishlist(wishlist);
        wishListRepository.deleteById(wishlist.getId());
    }

}
