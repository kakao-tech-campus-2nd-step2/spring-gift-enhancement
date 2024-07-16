package gift.controller.member.dto;

import gift.model.member.Member;
import gift.service.member.dto.MemberModel;

public class MemberResponse {

    public record Login(String token) {

        public static Login from(String token) {
            return new Login(token);
        }
    }

    public record Info(
        String email,
        String name
    ) {

        public static Info from(MemberModel.Info model) {
            return new Info(
                model.email(),
                model.name()
            );
        }
    }
}
