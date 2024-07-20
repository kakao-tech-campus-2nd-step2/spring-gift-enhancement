package gift.util.mapper;

import gift.user.dto.request.UserRegisterRequest;
import gift.user.dto.response.UserResponse;
import gift.user.entity.User;

public class UserMapper {

    public static User toUser(UserRegisterRequest request) {
        return User.builder()
            .email(request.email())
            .password(request.password())
            .build();
    }

    public static UserResponse toResponse(String token) {
        return new UserResponse(token);
    }

}
