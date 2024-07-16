package gift.event.event_publisher;

import gift.entity.Product;
import gift.event.event.ProductDeletionEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ProductDeletionEventPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public ProductDeletionEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publish(Long productId) {
        eventPublisher.publishEvent(new ProductDeletionEvent(this, productId));
    }
}
