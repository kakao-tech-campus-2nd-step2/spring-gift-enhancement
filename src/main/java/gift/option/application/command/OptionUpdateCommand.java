package gift.option.application.command;

import gift.option.domain.Option;

public record OptionUpdateCommand(
        Long id,
        String name,
        Integer quantity
) {
    public Option toOption() {
        return new Option(name, quantity);
    }
}
