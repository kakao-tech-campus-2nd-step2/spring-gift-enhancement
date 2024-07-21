package gift.mapper;

import gift.domain.option.Option;
import gift.web.dto.OptionDto;
import org.springframework.stereotype.Component;

@Component
public class OptionMappper {

    public OptionDto toDto(Option option) {
        return new OptionDto(
            option.getId(),
            option.getName(),
            option.getQuantity()
        );
    }
}
