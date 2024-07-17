package gift.controller.restcontroller;

import gift.controller.dto.request.ProductRequest;
import gift.model.Category;
import gift.model.Option;
import gift.model.Product;
import gift.repository.CategoryRepository;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductRestControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OptionRepository optionRepository;

    @Test
    void port() {
        assertThat(port).isNotZero();
    }

    @Test
    void create() {
        categoryRepository.save(new Category("상품권", "#123", "url", ""));
        var url = "http://localhost:" + port + "/api/v1/product";
        var request = new ProductRequest("product", 1_000, "Url", "상품권");
        var requestEntity = new RequestEntity<>(request, HttpMethod.POST, URI.create(url));
        var actual = restTemplate.exchange(requestEntity, String.class);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(actual.getHeaders().containsKey(HttpHeaders.LOCATION)).isTrue();
    }

    @Test
    void getOptions() {
        Category category = categoryRepository.save(new Category("상품권", "#123", "url", ""));
        Product product = productRepository.save(new Product("pname", 1_000, "purl", category));
        optionRepository.save(new Option("option1", 1000, product));
        var url = "http://localhost:" + port + "/api/v1/product/" + product.getId() + "/options";
        var requestEntity = new RequestEntity<>(HttpMethod.GET, URI.create(url));
        var actual = restTemplate.exchange(requestEntity, String.class);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody()).isNotEmpty();


    }
}