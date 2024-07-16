package gift.service;

import gift.dto.WishListDTO;

public interface WishListService {

    void addProduct(long memberId, long productId);

    void deleteProduct(long memberId, long productId);

    void updateProduct(long memberId, long productId, int productValue);

    WishListDTO getWishList(long memberId);

    WishListDTO getWishListPage(long memberId, int pageNumber, int pageSize);

}
