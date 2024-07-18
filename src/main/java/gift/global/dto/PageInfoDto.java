package gift.global.dto;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.domain.PageRequest;

// 페이지 관련 정보를 담을 dto. resolver에서 받아오는 정보이므로 swagger입력으로 인식하지 않도록 hidden으로 설정.
@Hidden
public record PageInfoDto(
    PageRequest pageRequest
) {

}
