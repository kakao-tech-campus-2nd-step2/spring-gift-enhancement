package gift.event.event_listener;

import gift.entity.Wish;
import gift.event.event.MemberDeletionEvent;
import gift.repository.WishRepository;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MemberDeletionEventListener {
    private final WishRepository wishRepository;

    public MemberDeletionEventListener(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    @Async
    @EventListener
    public void handleMemberDeletionEvent(MemberDeletionEvent event) {
        wishRepository.deleteByMemberId(event.getMemberId());
    }
}
