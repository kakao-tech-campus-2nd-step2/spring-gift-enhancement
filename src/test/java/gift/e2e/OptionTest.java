package gift.e2e;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/sql/initialize.sql", "/sql/insert_categories.sql",
    "/sql/insert_products.sql",
    "/sql/insert_options.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class OptionTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void Port() {
        assertThat(port).isNotZero();
    }

    @Test
    @DisplayName("get all options of product 1L")
    void getAllOptionsOfProduct1L() {
        // given
        var url = "http://localhost:" + port + "/api/products/1/options";
        var request = new RequestEntity<>(HttpMethod.GET, URI.create(url));

        // when
        var response = restTemplate.exchange(request, String.class);
        System.out.println(response);

        // then
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
    }

}
