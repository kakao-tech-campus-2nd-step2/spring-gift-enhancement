package gift.dto.response;

import gift.domain.Member;

public record MemberResponseDto (Long id, String email, String password) {
    public static MemberResponseDto from(final Member member){
        return new MemberResponseDto(member.getId(), member.getEmail(), member.getPassword());
    }
}
