package gift.permission.dto;

// 직접 db에 넣는 회원가입과 다르게 로그인은 조회만 하므로 유효성 검증을 하지 않습니다.
public record LoginRequestDto(
    String email,

    String password
) {

}
