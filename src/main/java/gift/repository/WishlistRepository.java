package gift.repository;

import gift.model.WishlistItem;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public
interface WishlistRepository extends JpaRepository<WishlistItem, Long> {

    Page<WishlistItem> findListByUserId(Long userId, Pageable pageable);
    List<WishlistItem> findListByUserId(Long userId);
}
