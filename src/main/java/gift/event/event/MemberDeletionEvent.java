package gift.event.event;

import gift.entity.Member;
import org.springframework.context.ApplicationEvent;

public class MemberDeletionEvent extends ApplicationEvent {
    private final Long memberId;

    public MemberDeletionEvent(Object source, Long memberId) {
        super(source);
        this.memberId = memberId;
    }

    public Long getMemberId() {
        return memberId;
    }
}
