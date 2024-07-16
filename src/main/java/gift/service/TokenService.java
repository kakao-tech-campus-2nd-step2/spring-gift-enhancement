package gift.service;

import gift.repository.TokenRepositoryInterface;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class TokenService {
    private final TokenRepositoryInterface tokenRepositoryInterface;

    public TokenService(TokenRepositoryInterface tokenRepositoryInterface) {
        this.tokenRepositoryInterface = tokenRepositoryInterface;
    }

    public String saveToken(Long userId) {
        String newToken = makeTokenFrom(userId.toString());
        return tokenRepositoryInterface.save(newToken);
    }

    public String makeTokenFrom(String userIdStr) {

        String tokenValue = "Basic " + Base64.getEncoder().encodeToString(userIdStr.getBytes());

        return tokenValue;
    }

    public Long getUserIdByDecodeTokenValue(String tokenValue) {
        String[] splitTokenValue = tokenValue.split(" ");
        String token = splitTokenValue[1];
        byte[] decodedBytes = Base64.getDecoder().decode(token);
        String userId = new String(decodedBytes);
        return Long.parseLong(userId);
    }

    public void deleteTokenOf(Long userId) {
        String newToken = makeTokenFrom(userId.toString());
        tokenRepositoryInterface.delete(newToken);
    }

    public void deleteToken(String token) {
        tokenRepositoryInterface.delete(token);
    }

}
