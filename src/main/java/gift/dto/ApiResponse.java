package gift.dto;

import org.springframework.http.HttpStatus;

public record ApiResponse(HttpStatus httpStatus, String message) {
}
