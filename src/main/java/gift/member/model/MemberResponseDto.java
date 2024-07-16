package gift.member.model;

public class MemberResponseDto {

    private final String accessToken;

    public MemberResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
