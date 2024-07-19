package gift.main.dto;

import gift.main.Exception.CustomException;
import gift.main.Exception.ErrorCode;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Validated
public record OptionListRequest(
        @Valid List<OptionRequest> optionRequests) {

    public OptionListRequest {
        if (optionRequests.isEmpty()) {
            throw new CustomException(ErrorCode.EMPTY_OPTION);
        }

        Set<OptionRequest> seen = new HashSet<>();
        for (OptionRequest optionRequest : optionRequests) {
            if (!seen.add(optionRequest)) {
                throw new CustomException(ErrorCode.DUPLICATE_OPTION);
            }
        }
    }
}
