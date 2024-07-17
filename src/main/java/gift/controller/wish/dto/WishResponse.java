package gift.controller.wish.dto;

import gift.model.wish.Wish;
import gift.service.wish.dto.WishModel;

public class WishResponse {

    public record Info(
        Long wishId,
        Long productId,
        String productName,
        Long count
    ) {

        public static Info from(WishModel.Info model) {
            return new Info(
                model.wishId(),
                model.productId(),
                model.productName(),
                model.count()
            );
        }
    }
}
