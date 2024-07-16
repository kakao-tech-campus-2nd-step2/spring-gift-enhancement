<<<<<<<< HEAD:src/main/java/gift/user/dto/request/UserRegisterRequest.java
package gift.user.dto.request;
========
package gift.dto.user.request;
>>>>>>>> 1304db5a (refactor(dto): DTO 디렉토리 구조 변경):src/main/java/gift/dto/user/request/UserRegisterRequest.java

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserRegisterRequest(
    @Email String email,
    @NotEmpty String password
) {

}
