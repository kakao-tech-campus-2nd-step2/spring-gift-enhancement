package gift.member.model;

import gift.wishlist.model.WishList;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "VARCHAR(255)")
    private String email;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private String password;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<WishList> wishLists = new ArrayList<>();

    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    protected Member() {}

    public Long getId() {
        return id;
    }

    public List<WishList> getWishLists() {
        return wishLists;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    // 이메일 업데이트 메소드
    public void updateEmail(@NotNull @NotBlank String newEmail) {
        if (newEmail == null || newEmail.trim().isEmpty()) {
            throw new IllegalArgumentException("이메일은 비어있을 수 없습니다.");
        }
        this.email = newEmail;
    }

    // 비밀번호 업데이트 메소드
    public void updatePassword(@NotNull @NotBlank String newPassword) {
        if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("비밀번호는 비어있을 수 없습니다.");
        }
        this.password = newPassword;
    }

    public boolean checkPassword(String rawPassword) {
        // PasswordEncoder 없이 구현
        return this.password.equals(rawPassword); // 단순 문자열 비교
    }

    public void validateLogin(String rawPassword) {
        if (!this.checkPassword(rawPassword)) {
            throw new IllegalArgumentException("옳지 않은 비밀번호 입니다.");
        }
    }
}