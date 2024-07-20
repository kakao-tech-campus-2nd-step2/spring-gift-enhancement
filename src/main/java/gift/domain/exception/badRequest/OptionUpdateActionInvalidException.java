package gift.domain.exception.badRequest;

public class OptionUpdateActionInvalidException extends BadRequestException {

    public OptionUpdateActionInvalidException() {
        super("the option update request's action is invalid. It must be `add` or `subtract`.");
    }
}
