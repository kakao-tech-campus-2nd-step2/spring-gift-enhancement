package gift.service;

import gift.dto.WishlistDTO;
import gift.model.Product;
import gift.model.User;
import gift.model.Wishlist;
import gift.repository.WishlistRepository;
import gift.repository.UserRepository;
import gift.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public WishlistService(WishlistRepository wishlistRepository, UserRepository userRepository, ProductRepository productRepository){
        this.wishlistRepository = wishlistRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public void addWishlist(WishlistDTO wishlistDTO){
        User user = userRepository.findByEmail(wishlistDTO.getUserId());
        Product product = productRepository.findById(wishlistDTO.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + wishlistDTO.getProductId()));

        Wishlist wishlist = new Wishlist(user, product);
        wishlistRepository.save(wishlist);
    }

    @Transactional(readOnly = true)
    public Page<WishlistDTO> loadWishlist(String userId, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return wishlistRepository.findByUserEmail(userId, pageable)
                .map(wishlist -> new WishlistDTO(wishlist.getUser().getEmail(), wishlist.getProduct().getId()));
    }

    @Transactional
    public void deleteWishlist(String userId, Long productId){
        wishlistRepository.deleteByUserEmailAndProductId(userId, productId);
    }

    @Transactional(readOnly = true)
    public Page<Product> getProductsFromWishlist(String userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return wishlistRepository.findByUserEmail(userId, pageable)
                .map(Wishlist::getProduct);
    }
}