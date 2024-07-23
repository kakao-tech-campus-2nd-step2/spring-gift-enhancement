package study;

import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

public class RestTemplateTest {
  private final RestTemplate client =  new RestTemplateBuilder().build();

  @Test
  void  test1(){
    var url = "https://kauth.kakao.com/oauth/token";
    var headers = new HttpHeaders();
    headers.add(HttpHeaders.CONTENT_TYPE, APPLICATION_FORM_URLENCODED_VALUE);

    var body = new LinkedMultiValueMap<String,String>();
    body.add("grant_type", "authorization_code");
    body.add("client_id","3067383950805f02fd78a974d772f63f");
    body.add("redirect_url","http://localhost:8080");
    body.add("code","KVUZpoUKDZA7Duh5s-1ZO2J4cbok0NJPFySJvE-GpdIyszrHFa6HSQAAAAQKPXLqAAABkNg_TUnMISgqRbFCUQ");

    var request = new RequestEntity<>(body, headers, HttpMethod.POST, URI.create(url));
    var response = client.exchange(request,String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    System.out.println(response);
  }
}
