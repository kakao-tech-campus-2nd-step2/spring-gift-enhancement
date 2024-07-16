package gift.dto.request;

import gift.domain.Member;

public record MemberRequestDto(Long id, String email, String password) {
    public Member toEntity(){
        return new Member(this.id, this.email, this.password);
    }
}
