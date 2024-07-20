package gift.model.member;

import gift.model.wish.Wish;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, columnDefinition = "VARCHAR(255)")
    private String email;

    @Column(nullable = false, columnDefinition = "VARCHAR(255)")
    private String password;

    @Column(nullable = false, columnDefinition = "VARCHAR(255)")
    private String role;

    protected Member() {
    }
  
    public Member(String email, String password,String role) {
        this.email = email;
        this.password = password;
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public boolean isPasswordEqual(String inputPassword) {
        return this.password.equals(inputPassword);
    }
}