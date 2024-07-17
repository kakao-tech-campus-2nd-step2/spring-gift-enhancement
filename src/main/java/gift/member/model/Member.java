package gift.member.model;

import gift.wishlist.model.WishList;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
    private List<WishList> wishLists = new ArrayList<>();

    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public Long getMemberId() {
        return memberId;
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

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

}