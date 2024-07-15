package gift.service;

import gift.converter.WishListConverter;
import gift.dto.PageRequestDTO;
import gift.dto.WishListDTO;
import gift.model.Product;
import gift.model.User;
import gift.model.WishList;
import gift.repository.ProductRepository;
import gift.repository.UserRepository;
import gift.repository.WishListRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishListService {

    private final WishListRepository wishListRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public WishListService(WishListRepository wishListRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.wishListRepository = wishListRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public Pageable createPageRequest(PageRequestDTO pageRequestDTO) {
        Sort sort;
        if (pageRequestDTO.getDirection().equalsIgnoreCase(Sort.Direction.DESC.name())) {
            sort = Sort.by(pageRequestDTO.getSortBy()).descending();
        } else {
            sort = Sort.by(pageRequestDTO.getSortBy()).ascending();
        }

        return PageRequest.of(pageRequestDTO.getPage(), pageRequestDTO.getSize(), sort);
    }

    public Page<WishListDTO> getWishListByUser(String email, Pageable pageable) {
        User user = userRepository.findByEmail(email);
        Page<WishList> wishLists = wishListRepository.findByUser(user, pageable);
        return wishLists.map(WishListConverter::convertToDTO);
    }

    public void addProductToWishList(String email, Long productId) {
        User user = userRepository.findByEmail(email);
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));

        WishList newWishList = new WishList(null, user, product);
        wishListRepository.save(newWishList);
    }

    @Transactional
    public void removeProductFromWishList(String email, Long productId) {
        User user = userRepository.findByEmail(email);
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));

        WishList wishList = wishListRepository.findByUserAndProduct(user, product)
            .orElseThrow(() -> new IllegalArgumentException("Wishlist not found for user: " + email));

        wishListRepository.delete(wishList);
    }
}