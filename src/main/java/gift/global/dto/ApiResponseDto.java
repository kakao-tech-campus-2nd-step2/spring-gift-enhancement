package gift.global.dto;

// 반환이 없는 api의 반환은 이 dto를 반환합니다.
public record ApiResponseDto(
    boolean success,
    Object data,
    String error
) {

    // 성공하고 아무 것도 반환 안 하는 경우
    public static ApiResponseDto succeed() {
        return new ApiResponseDto(true, null, null);
    }

    // 실패한 경우
    public static ApiResponseDto fail(String error) {
        return new ApiResponseDto(false, null, error);
    }

    // 성공하고 데이터를 넘겨주는 경우
    public static ApiResponseDto succeed(Object data) {
        return new ApiResponseDto(true, data, null);
    }
}
