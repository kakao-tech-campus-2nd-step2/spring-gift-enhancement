package study;

import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@ConfigurationPropertiesScan
@ConfigurationProperties(prefix = "kakao")
record KakaoProperties(
        String clientId,
        String redirectUrl
) {}

@ActiveProfiles("test")
@SpringBootTest
public class RestClientTest {
    private final RestClient client = RestClient.builder().build();

    @Autowired
    private KakaoProperties properties;

    @MockBean
    private RedissonClient redissonClient;


    @Test
    void test2() {
        System.out.println(properties);
    }

    @Test
    void test1() {
        var url = "https://kauth.kakao.com/oauth/token";
        final var body = createBody();
        var response = client.post()
                .uri(URI.create(url))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(body)     // request body
                .retrieve()
                .toEntity(String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        System.out.println(response);
    }

    private @NotNull LinkedMultiValueMap<String, String> createBody() {
        var code = "ylvvgf83RQzWp0Q9c77urGLMZ54lUEQMbZ3JDsWojGHv0FFFjdkFzgAAAAQKPXPsAAABkNg9ZbzRDLJpR7eCqA";
        var body = new LinkedMultiValueMap<String, String>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", properties.clientId());
        body.add("redirect_uri", properties.redirectUrl());
        body.add("code", code);
        return body;
    }
}
