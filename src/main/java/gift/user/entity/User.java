package gift.user.entity;

import gift.exception.CustomException;
import gift.exception.ErrorCode;
import gift.wish.entity.Wish;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.Set;
import java.util.regex.Pattern;

@Entity
@Table(
    name = "users",
    uniqueConstraints = @UniqueConstraint(columnNames = {"email"}, name = "uk_users")
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserRole> userRoles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Wish> wishes;

    protected User() {
    }

    private User(Builder builder) {
        validateEmail(builder.email);
        this.email = builder.email;
        this.password = builder.password;
        this.userRoles = builder.userRoles;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Set<UserRole> getRoles() {
        return userRoles;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    private void validateEmail(String email) {
        final String EMAIL_REGEX = "^(.+)@(\\S+)$";
        Pattern emailPattern = Pattern.compile(EMAIL_REGEX);
        if (!emailPattern.matcher(email).matches()) {
            throw new CustomException(ErrorCode.INVALID_EMAIL);
        }
    }

    public static class Builder {

        private String email;
        private String password;
        private Set<UserRole> userRoles;

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder userRoles(Set<UserRole> userRoles) {
            this.userRoles = userRoles;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

}
