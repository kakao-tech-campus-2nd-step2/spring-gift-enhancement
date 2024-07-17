package gift.event.event;

import org.springframework.context.ApplicationEvent;

public class ProductDeletionEvent extends ApplicationEvent {
    private final Long productId;

    public ProductDeletionEvent(Object source, Long productId) {
        super(source);
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
