package gift.event.event_publisher;

import gift.entity.Member;
import gift.event.event.MemberDeletionEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class MemberDeletionEventPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public MemberDeletionEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publish(Member member) {
        eventPublisher.publishEvent(new MemberDeletionEvent(this, member));
    }

}
