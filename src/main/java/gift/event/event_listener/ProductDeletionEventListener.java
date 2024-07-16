package gift.event.event_listener;

import gift.event.event.ProductDeletionEvent;
import gift.repository.WishRepository;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ProductDeletionEventListener {
    private final WishRepository wishRepository;

    public ProductDeletionEventListener(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    @Async
    @EventListener
    public void onProductDeletionEvent(ProductDeletionEvent event) {
        wishRepository.deleteByProductId(event.getProductId());
    }
}
