package study;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;


@ConfigurationProperties("kakao")
record KakaoProperties(
        String clientId,
        String redirect_url
){

}
@ActiveProfiles("test")
@SpringBootTest
public class RestClientTest {
  private final RestClient client = RestClient.builder().build();

  @Autowired
  private KakaoProperties properties;

  @Test
  void test2(){
    assertThat(properties.clientId()).isNotEmpty();
    assertThat(properties.redirect_url()).isNotEmpty();
    assertThat(properties.redirect_url()).isEqualTo("http://localhost:8081");
  }
  @Test
  void test1(){
    // https://kauth.kakao.com/oauth/authorize?scope=talk_message&response_type=code&redirect_uri=http://localhost:8080&client_id=3067383950805f02fd78a974d772f63f
    var url = "https://kauth.kakao.com/oauth/token";
    var body = new LinkedMultiValueMap<String,String>();
    new KakaoProperties("3067383950805f02fd78a974d772f63f","http://localhost:8081 ");
    body.add("grant_type", "authorization_code");
    body.add("client_id",properties.clientId());
    body.add("redirect_url",properties.redirect_url());
    body.add("code","twqw4s1lCM5Ixw7kofMDw_jpUNzR5FTIycBGhG1axX1kNUyZlGGfZQAAAAQKKw0eAAABkNhBDiyIenTzhLqDRQ");
    var response = client.post()
            .uri(URI.create(url))
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(body)
            .retrieve()
            .toEntity(String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    System.out.println(response);
  }
}
