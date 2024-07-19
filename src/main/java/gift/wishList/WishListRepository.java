package gift.wishList;

import gift.option.Option;
import gift.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {
    public List<WishList> findByUser(User user);

    public Page<WishList> findByUser(User user, Pageable pageable);

    public Optional<WishList> findByUserAndOptionId(User user, Long optionID);
}
