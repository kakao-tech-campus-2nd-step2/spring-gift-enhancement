package gift.wishlist.dto;

// 위시리스트를 조작해서 나온 결과를 담는 dto
public record WishListResponseDto(
    long wishListId,
    long userId,
    long productId,
    String name,
    int price,
    String imageUrl,
    long categoryId,
    String categoryName,
    String categoryImageUrl,
    int quantity) {

}
