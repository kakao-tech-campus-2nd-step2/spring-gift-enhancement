package gift.domain.exception.notFound;

public class MemberNotFoundException extends NotFoundException {

    public MemberNotFoundException() {
        super("User not found.");
    }
}
