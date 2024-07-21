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
    private Long id;

    public ProductResponseDto(Long id, String name, Integer price, String url) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.url = url;
    }

    public ProductResponseDto(String name, Integer price, String url) {
        this(null, name, price, url);
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

}
