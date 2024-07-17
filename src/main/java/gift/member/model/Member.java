package gift.member.model;

import gift.wishlist.model.WishList;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq")
    @SequenceGenerator(name = "member_seq", sequenceName = "member_seq", allocationSize = 1)
    private Long memberId;

    @Column(nullable = false, columnDefinition = "VARCHAR(255)")
    private String email;

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private String password;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<WishList> wishLists = new ArrayList<>();

    // 기본 생성자
    public Member() {
    }

    // 매개변수가 있는 생성자
    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Long getMemberId() {
        return memberId;
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

    public void updateEmail(@NotNull @NotBlank String newEmail) {
        if (newEmail == null || newEmail.trim().isEmpty()) {
            throw new IllegalArgumentException("이메일은 비어있을 수 없습니다.");
        }
        this.email = newEmail; // 현재 인스턴스의 이메일을 업데이트
    }

    public void updatePassword(@NotNull @NotBlank String newPassword) {
        if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("비밀번호는 비어있을 수 없습니다.");
        }
        this.password = newPassword; // 현재 인스턴스의 비밀번호를 업데이트
    }

    // 비밀번호 검증 메소드
    public boolean checkPassword(String rawPassword, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(rawPassword, this.password);
    }

    // 로그인 검증 메소드
    public void validateLogin(String rawPassword, PasswordEncoder passwordEncoder) {
        if (!checkPassword(rawPassword, passwordEncoder)) {
            throw new IllegalArgumentException("옳지 않은 이메일이나 비밀번호 입니다.");
        }
    }
}