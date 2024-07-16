package gift.controller;

import static org.assertj.core.api.Assertions.assertThat;

import gift.domain.category.entity.Category;
import gift.domain.product.dto.ProductRequest;
import gift.domain.product.entity.Product;
import gift.domain.product.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.net.URI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository productRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void readAll() {
        var url = "http://localhost:" + port + "/api/products";
        var request = new RequestEntity<>(HttpMethod.GET, URI.create(url));

        var actual = restTemplate.exchange(request, String.class);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void read() {
        Category savedCategory = new Category(1L, "test", "color", "image", "description");
        Product product = productRepository.save(new Product("test", 1000, "test.jpg", savedCategory));
        System.out.println("테스트" + product.getName() + "id 값" + product.getId());

        var url = "http://localhost:" + port + "/api/products/1";
        var request = new RequestEntity<>(HttpMethod.GET, URI.create(url));

        var actual = restTemplate.exchange(request, String.class);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Product 생성 API 테스트")
    void create() {
        var request = new ProductRequest("product", 1000, "image.jpg", 1L);
        var url = "http://localhost:" + port + "/api/products";
        var requestEntity = new RequestEntity<>(request, HttpMethod.POST, URI.create(url));

        var actual = restTemplate.exchange(requestEntity, String.class);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void update() {
        Category savedCategory = new Category(1L, "test", "color", "image", "description");
        Product product = productRepository.save(new Product("test", 1000, "test.jpg", savedCategory));
        var id = 1L;
        var request = new ProductRequest("product", 1000, "image.jpg", 1L);
        var url = "http://localhost:" + port + "/api/products/" + id;
        var requestEntity = new RequestEntity<>(request, HttpMethod.PUT, URI.create(url));

        var actual = restTemplate.exchange(requestEntity, String.class);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void delete() {
        Category savedCategory = new Category(1L, "test", "color", "image", "description");
        Product product = productRepository.save(new Product("test", 1000, "test.jpg", savedCategory));
        var id = 1L;
        var url = "http://localhost:" + port + "/api/products/" + id;

        var requestEntity = new RequestEntity<>(HttpMethod.DELETE, URI.create(url));

        var actual = restTemplate.exchange(requestEntity, String.class);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}