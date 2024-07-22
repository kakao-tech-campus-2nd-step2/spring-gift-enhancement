package gift.global.dto;

import io.swagger.v3.oas.annotations.Hidden;

// 사용자가 직접 보내진 않지만, 사용자를 위해 보내야만 하는 정보들을 담는 dto
@Hidden
public record UserInfoDto(
    Long userId) {

}
