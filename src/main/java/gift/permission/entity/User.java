package gift.permission.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private boolean isAdmin;

    protected User() {

    }

    public User(String email, String password, boolean isAdmin) {
        this.userId = null;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void grantAdmin() {
        this.isAdmin = true;
    }

    public void revokeAdmin() {
        this.isAdmin = false;
    }

    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}