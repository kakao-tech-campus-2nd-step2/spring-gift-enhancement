package gift.service;

import gift.dto.OptionDTO;
import gift.dto.WishlistDTO;
import gift.model.Option;
import gift.model.Product;
import gift.model.SiteUser;
import gift.model.Wishlist;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import gift.repository.UserRepository;
import gift.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OptionRepository optionRepository;

    @Autowired
    public WishlistServiceImpl(WishlistRepository wishlistRepository, UserRepository userRepository, ProductRepository productRepository, OptionRepository optionRepository) {
        this.wishlistRepository = wishlistRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.optionRepository = optionRepository;
    }

    @Override
    public List<WishlistDTO> getWishlistByUser(String username) {
        List<Wishlist> wishlistEntities = wishlistRepository.findByUserUsername(username);
        return wishlistEntities.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @Override
    public void addToWishlist(String username, Long productId, int quantity, List<Map<String, Object>> options) {
        SiteUser user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("Invalid username: " + username));
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + productId));

        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setProduct(product);
        wishlist.setQuantity(quantity);
        wishlist.setPrice(product.getPrice());
        wishlistRepository.save(wishlist);

        options.forEach(option -> {
            Long optionId = Long.parseLong(option.get("id").toString());
            int optionQuantity = Integer.parseInt(option.get("quantity").toString());
            Option optionEntity = optionRepository.findById(optionId).orElseThrow(() -> new IllegalArgumentException("Invalid option ID: " + optionId));

            // 옵션을 Wishlist에 추가하는 로직 수정
            Wishlist optionWishlist = new Wishlist();
            optionWishlist.setUser(user);
            optionWishlist.setProduct(product); // 옵션의 상품으로 설정
            optionWishlist.setQuantity(optionQuantity);
            optionWishlist.setPrice(optionEntity.getPrice() * optionQuantity); // 옵션의 총 가격 설정
            wishlistRepository.save(optionWishlist);
        });
    }

    @Override
    public void removeFromWishlist(Long id) {
        wishlistRepository.deleteById(id);
    }

    @Override
    public void updateQuantity(Long id, int quantity) {
        Wishlist wishlist = wishlistRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid wishlist ID: " + id));
        wishlist.setQuantity(quantity);
        wishlistRepository.save(wishlist);
    }

    private WishlistDTO convertToDTO(Wishlist wishlist) {
        List<OptionDTO> optionDTOs = wishlist.getProduct().getOptions().stream()
            .map(OptionDTO::convertToDTO)
            .collect(Collectors.toList());

        WishlistDTO wishlistDTO = new WishlistDTO(
            wishlist.getId(),
            wishlist.getProduct().getId(),
            wishlist.getUser().getUsername(),
            wishlist.getQuantity(),
            wishlist.getProduct().getName(),
            wishlist.getPrice(),
            wishlist.getProduct().getImageUrl()
        );
        wishlistDTO.setOptions(optionDTOs);
        return wishlistDTO;
    }

    @Override
    public Page<WishlistDTO> getWishlistByUser1(String username, Pageable pageable) {
        Page<Wishlist> wishlistEntities = wishlistRepository.findByUserUsername(username, pageable);
        return wishlistEntities.map(this::convertToDTO);
    }
}
