package gift.entity;

import gift.dto.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

import static gift.dto.Role.ADMIN;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Wishlist> wishlists;

    // 기본 생성자
    public Member() {
    }


    // 매개변수 있는 생성자
    public Member(String email, String password) {
        this.email = email;
        this.password = password;
        this.role = Role.USER;
    }

    // admin 계정을 생성자로 만들기 위해 추가함
    public Member(String email, String password, Role role) {
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

    public Role getRole() {
        return role;
    }


}
