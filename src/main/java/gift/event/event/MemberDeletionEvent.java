package gift.event.event;

import gift.entity.Member;
import org.springframework.context.ApplicationEvent;

public class MemberDeletionEvent extends ApplicationEvent {
    private final Member member;

    public MemberDeletionEvent(Object source, Member member) {
        super(source);
        this.member = member;
    }

    public Member getMember() {
        return member;
    }
}
