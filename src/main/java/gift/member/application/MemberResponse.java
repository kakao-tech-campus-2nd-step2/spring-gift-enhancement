package gift.member.application;

import gift.member.domain.Member;
import gift.member.presentation.request.ResolvedMember;

public record MemberResponse(
        Long id,
        String email,
        String password
) {
    public static MemberResponse from(Member member) {
        return new MemberResponse(member.getId(), member.getEmail(), member.getPassword());
    }
}
