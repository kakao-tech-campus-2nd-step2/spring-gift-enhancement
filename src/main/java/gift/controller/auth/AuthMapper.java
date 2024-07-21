package gift.controller.auth;

import gift.controller.member.MemberResponse;

public class AuthMapper {

    public static LoginResponse toLoginResponse(MemberResponse member) {
        return new LoginResponse(member.id(), member.email(), member.nickName(), member.grade());
    }
}