package gift.dto;

import gift.entity.User;

public class UserResponseDto {

    private final Long id;
    private final String email;
    private final String password;

    public UserResponseDto(Long id, String email, String password) {
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

    public static UserResponseDto fromEntity(User user) {
        return new UserResponseDto(user.getId(), user.getEmail(), user.getPassword());

    }
}
