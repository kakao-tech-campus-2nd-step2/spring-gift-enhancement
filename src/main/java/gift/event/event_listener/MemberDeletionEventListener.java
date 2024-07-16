package gift.event.event_listener;

import gift.entity.Wish;
import gift.event.event.MemberDeletionEvent;
import gift.repository.WishRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MemberDeletionEventListener {
    private final WishRepository wishRepository;

    public MemberDeletionEventListener(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    @EventListener
    public void handleMemberDeletionEvent(MemberDeletionEvent event) {
        List<Wish> wishes = event.getMember().getAllWishes();

        wishRepository.deleteAll(wishes);
    }
}
