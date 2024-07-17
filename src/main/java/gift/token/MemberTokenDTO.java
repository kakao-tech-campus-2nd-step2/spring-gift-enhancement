package gift.token;

import gift.member.MemberDTO;

public class MemberTokenDTO {

    private String email;

    public MemberTokenDTO(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
