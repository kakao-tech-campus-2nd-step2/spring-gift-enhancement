package gift.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import gift.domain.user.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserDTO {

    @Email
    private String email;
    @NotBlank
    private String password;

    @JsonCreator
    public UserDTO(@JsonProperty("email") String email, @JsonProperty("password") String password) {
        this.email = email;
        this.password = password;
    }

    public UserDTO() {
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public User toUser() {
        return new User(this.email, this.password); // dto to entity
    }

    @Override
    public String toString() {
        return "UserDTO{" +
               "email='" + email + '\'' +
               ", password='" + password + '\'' +
               '}';
    }
}
