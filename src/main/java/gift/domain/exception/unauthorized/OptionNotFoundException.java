package gift.domain.exception.unauthorized;

public class OptionNotFoundException extends UnauthorizedException {

    public OptionNotFoundException() {
        super("The option was not found.");
    }
}
