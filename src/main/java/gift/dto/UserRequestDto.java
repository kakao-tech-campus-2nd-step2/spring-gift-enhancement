package gift.dto;

public class UserRequestDto {

    private final Long id;
    private final String email;
    private final String password;

    public UserRequestDto(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
