package gift.wishList;

import gift.option.Option;
import gift.option.OptionRepository;
import gift.product.Product;
import gift.user.User;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class WishListService {
    private final WishListRepository wishListRepository;
    private final OptionRepository optionRepository;

    public WishListService(WishListRepository wishListRepository, OptionRepository optionRepository) {
        this.wishListRepository = wishListRepository;
        this.optionRepository = optionRepository;
    }

    public WishListResponse addWish(WishListRequest wishListDTO, User user) {
        Option option = optionRepository.findById(wishListDTO.getOptionID()).orElseThrow();
        Product product = option.getProduct();

        Optional<WishList> exist = wishListRepository.findByUserAndOptionId(user, option.getId());
        if(exist.isPresent()){
            return updateCount(new CountDTO(wishListDTO.count + exist.get().getCount()), exist.get().getId());
        }

        WishList wishList = new WishList(wishListDTO.getCount());
        user.addWishList(wishList);
        option.addWishList(wishList);
        product.addWishList(wishList);
        wishListRepository.save(wishList);
        return new WishListResponse(wishList);
    }

    public List<WishListResponse> findByUser(User user) {
        List<WishList> wishLists = wishListRepository.findByUser(user);
        List<WishListResponse> wishListDTOS = new ArrayList<>();
        wishLists.forEach((wishList -> wishListDTOS.add(new WishListResponse(wishList))));
        return wishListDTOS;
    }

    public WishListResponse updateCount(CountDTO count, Long id) {
        WishList wishList = wishListRepository.findById(id).orElseThrow();
        wishList.setCount(count.getCount());
        return new WishListResponse(wishList);
    }

    public void deleteByID(Long id) {
        WishList wishList = wishListRepository.findById(id).orElseThrow();
        wishList.getUser().removeWishList(wishList);
        wishList.getOption().removeWishList(wishList);
        wishListRepository.deleteById(id);
    }

    public Page<WishListResponse> getWishListsPages(int pageNum, int size, User user, String sortBy, String sortDirection) {
        Pageable pageable = PageRequest.of(pageNum, size, Sort.by(Sort.Order.asc(sortBy)));
        if (Objects.equals(sortDirection, "desc")) {
            pageable = PageRequest.of(pageNum, size, Sort.by(Sort.Order.desc(sortBy)));
        }

        Page<WishList> wishLists = wishListRepository.findByUser(user, pageable);
        return wishLists.map(WishListResponse::new);

    }
}
