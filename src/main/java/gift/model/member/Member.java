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

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Wish> wishes;

    protected Member() {
    }

    public Member(String email, String password) {
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

    public boolean isPasswordEqual(String inputPassword) {
        return this.password.equals(inputPassword);
    }
}