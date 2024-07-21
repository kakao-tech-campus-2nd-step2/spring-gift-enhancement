package gift.dto;

import gift.entity.Category;
import gift.entity.Option;
import gift.entity.Product;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ProductResponseDto {

    private String name;
    private String url;
    private Integer price;
    private List<Product> proudcts;
    public List<Option> options;
    private Long id;
    private Category category;
    private HttpStatus httpStatus;

    public ProductResponseDto(Long id, String name, Integer price, String url, Category category, List<Option> options, HttpStatus httpStatus) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.url = url;
        this.category = category;
        this.options = options;
        this.httpStatus = httpStatus;
    }

    public ProductResponseDto(Long id, String name, Integer price, String url, Category category, List<Option> options) {
        this(id, name, price, url, category, options, null);
    }

    public ProductResponseDto(String name, Integer price, String url, Category category, List<Option> options, HttpStatus httpStatus) {
        this(null, name, price, url, category, options, httpStatus);
    }

    public ProductResponseDto(String name, Integer price, String url, Category category, List<Option> options) {
        this(null, name, price, url, category, options, null);
    }

    public ProductResponseDto(List<Product> all) {
        this.proudcts = all;
    }

    public ProductResponseDto(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public static ProductResponseDto fromEntity(Product product) {
        return new ProductResponseDto(product.getName(), product.getPrice(), product.getUrl(), product.getCategory(), product.getOptions());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getUrl() {
        return url;
    }

    public Category getCategory() {
        return category;
    }

    public List<Option> getOptions() {
        return options;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
