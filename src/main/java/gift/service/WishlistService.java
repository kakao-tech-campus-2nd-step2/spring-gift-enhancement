package gift.service;

import gift.dto.WishlistDTO;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WishlistService {
    List<WishlistDTO> getWishlistByUser(String username);
    void addToWishlist(String username, Long productId, int quantity, List<Map<String, Object>> options);
    void removeFromWishlist(Long id);
    void updateQuantity(Long id, int quantity, Long optionId);
    Page<WishlistDTO> getWishlistByUser1(String username, Pageable pageable);
    WishlistDTO getWishlistById(Long id);
}
