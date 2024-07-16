<<<<<<<< HEAD:src/main/java/gift/wish/dto/response/WishResponse.java
package gift.wish.dto.response;
========
package gift.dto.wish.response;
>>>>>>>> 1304db5a (refactor(dto): DTO 디렉토리 구조 변경):src/main/java/gift/dto/wish/response/WishResponse.java

import gift.product.entity.Product;

public record WishResponse(
    Long id,
    Product product,
    Integer quantity
) {

}
