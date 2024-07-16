package gift.dto.response;

import gift.domain.Product;

public class ProductResponse {
    private final Long id;
    private final String name;
    private final Integer price;
    private final String imageUrl;
    private final String categoryName;

    public ProductResponse(Long id, String name, Integer price, String imageUrl, String categoryName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.categoryName = categoryName;
    }

    public Long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl(){
        return imageUrl;
    }

    public String getCategoryName(){
        return categoryName;
    }


    public static ProductResponse EntityToResponse(Product product){
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getImageUrl(), product.getCategory().getName());
    }
}
