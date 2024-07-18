package gift.dto;

import gift.entity.Member;

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

    public static UserResponseDto fromEntity(Member member) {
        return new UserResponseDto(member.getId(), member.getEmail(), member.getPassword());

    }
}
