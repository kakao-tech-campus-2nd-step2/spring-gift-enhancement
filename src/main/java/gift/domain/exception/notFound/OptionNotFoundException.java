package gift.domain.exception.notFound;

public class OptionNotFoundException extends NotFoundException {

    public OptionNotFoundException() {
        super("The option was not found.");
    }
}
