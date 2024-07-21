package gift.member;

import gift.member.dto.MemberReqDto;
import gift.member.entity.Member;

public class MemberFixture {

    public static Member createMember(String email, String password) {
        return new Member(email, password);
    }

    public static MemberReqDto createMemberReqDto(String email, String password) {
        return new MemberReqDto(email, password);
    }
}
