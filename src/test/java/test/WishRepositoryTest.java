package test;


import gift.entity.Category;
import gift.entity.Member;
import gift.entity.Option;
import gift.entity.Product;
import gift.entity.Wish;
import gift.repository.CategoryRepository;
import gift.repository.MemberRepository;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import gift.repository.WishRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class WishRepositoryTest {
    private WishRepository wishes;
    private ProductRepository products;
    private CategoryRepository categories;
    private OptionRepository options;
    private MemberRepository members;

    public WishRepositoryTest() {
    }

    @Test
    public void save() {
        Category category = categories.save(new Category("카테고리1", "빨강", "설명1", "카테고리 이미지url"));

        List<Option> optionList = new ArrayList<>();
        Option newOption = options.save(new Option("옵션이름1", 10L));
        optionList.add(newOption);

        Product product = products.save(new Product("상품1", 1000, "url", category, optionList));
        Member member = members.save(new Member("이메일1", "비밀번호"));

        Wish wish = wishes.save(new Wish(product, member));

        assertThat(wish).isNotNull();
        assertThat(wish.getProduct()).isNotNull();
        assertThat(wish.getMember()).isNotNull();
        assertThat(wish.getProductId()).isNotNull();
        assertThat(wish.getMemberId()).isNotNull();

    }

}
