package gift.repository;

import gift.model.Category;
import gift.model.Option;
import gift.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository products;

    @Autowired
    private CategoryRepository categories;

    @Autowired
    private OptionReposityory optionReposityory;

    @DisplayName("새로운 상품 저장")
    @Test
    void save(){
        Category category = new Category("category");
        categories.save(category);
        Option option = new Option("optionA, optionB");
        optionReposityory.save(option);

        Product expected = new Product("newProduct", 1000, "newimg.img", category, option);
        Product actual = products.save(expected);
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("모든 상품 리스트 반환")
    @Test
    void getAllProduct(){
        Category category = new Category("category");
        categories.save(category);
        Option option1 = new Option("optionA, optionB");
        optionReposityory.save(option1);
        Option option2 = new Option("optionA, optionB");
        optionReposityory.save(option2);
        Option option3 = new Option("optionA, optionB");
        optionReposityory.save(option3);

        products.save(new Product("Product1", 1000, "1.img", category, option1));
        products.save(new Product("Product2", 5000, "2.img", category, option2));
        products.save(new Product("Product3", 15000, "3.img", category, option3));
        List<Product> actual = products.findAll();
        List<Product> expected = List.of(new Product("Product1", 1000, "1.img", category, option1),
                new Product("Product2", 5000, "2.img", category, option2),
                new Product("Product3", 15000, "3.img", category, option3));
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("이름(unique하다고가정)으로 해당하는 객체 반환")
    @Test
    void getProductByName(){
        Category category = new Category("category");
        categories.save(category);
        Option option = new Option("optionA, optionB");
        optionReposityory.save(option);

        Product expected = new Product("Product1", 1000, "1.img", category, option);
        products.save(expected);
        Product actual = products.findByName("Product1").orElseThrow();
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("ID로 상품 반환")
    @Test
    void getProductByID(){
        Category category = new Category("category");
        categories.save(category);
        Option option = new Option("optionA, optionB");
        optionReposityory.save(option);

        products.save(new Product("Product1", 1000, "1.img", category, option));
        Product expected = products.findByName("Product1").orElseThrow();
        Product actual = products.findById(expected.getId()).orElseThrow();
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("ID로 상품 삭제")
    @Test
    void deleteByID(){
        Category category = new Category("category");
        categories.save(category);
        Option option = new Option("optionA, optionB");
        optionReposityory.save(option);

        products.save(new Product("Product1", 1000, "1.img", category, option));
        Product product = products.findByName("Product1").orElseThrow();
        products.deleteById(product.getId());
        assertThat(products.existsById(product.getId())).isFalse();
    }
}