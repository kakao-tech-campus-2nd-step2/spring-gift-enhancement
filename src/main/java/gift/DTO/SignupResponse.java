package gift.DTO;

public class SignupResponse {

    private String token;

    public SignupResponse(String email) {
        this.token = "Welcome, " + email + "!";
    }

    public String getToken() {
        return token;
    }
}
