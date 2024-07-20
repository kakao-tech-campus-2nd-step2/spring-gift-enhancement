package gift.dto;

import gift.entity.Member;
import gift.entity.Wish;
import org.springframework.http.HttpStatus;

import java.util.List;

public class MemberResponseDto {

    private Long id;
    private String email;
    private String password;
    private List<Wish> wishes;
    private HttpStatus httpStatus;

    private String token;

    private List<Member> members;

    public MemberResponseDto(Long id, String email, String password, List<Wish> wishes, HttpStatus httpStatus) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.wishes = wishes;
        this.httpStatus = httpStatus;
    }

    public MemberResponseDto(String email, String password, List<Wish> wishes, HttpStatus httpStatus) {
        this.email = email;
        this.password = password;
        this.wishes = wishes;
        this.httpStatus = httpStatus;
    }

    public MemberResponseDto(Long id, String email, String password, List<Wish> wishes) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.wishes = wishes;
        this.httpStatus = null;
    }

    public MemberResponseDto(String email, String password, List<Wish> wishes) {
        this.email = email;
        this.password = password;
        this.wishes = wishes;
        this.httpStatus = null;
    }

    public MemberResponseDto(List<Member> members) {
        this.members = members;
        this.httpStatus = null;
    }

    public MemberResponseDto(Member actualMember, String token) {
        this.id = actualMember.getId();
        this.email = actualMember.getEmail();
        this.password = actualMember.getPassword();
        this.wishes = actualMember.getWishes();
        this.token = actualMember.getToken();
    }

    public static MemberResponseDto fromEntity(Member member) {
        return new MemberResponseDto(member.getId(), member.getEmail(), member.getPassword(),member.getWishes(),null);
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

    public List<Wish> getWishes() {
        return wishes;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
