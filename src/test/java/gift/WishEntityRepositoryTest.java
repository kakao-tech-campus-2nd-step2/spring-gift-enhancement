package gift;

import gift.Model.Entity.CategoryEntity;
import gift.Model.Entity.MemberEntity;
import gift.Model.Entity.ProductEntity;
import gift.Model.Entity.WishEntity;
import gift.Model.Role;
import gift.Repository.CategoryRepository;
import gift.Repository.MemberRepository;
import gift.Repository.ProductRepository;
import gift.Repository.WishRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
public class WishEntityRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeTestClass
    public void setup(){
        categoryRepository.save(new CategoryEntity("test1","test2","test3","test4"));
    }

    @Test
    void save(){
        MemberEntity member = new MemberEntity("a","b", Role.CONSUMER);
        ProductEntity product = new ProductEntity(categoryRepository.findById(1L).get(),"a",1,"b");
        memberRepository.save(member);
        productRepository.save(product);
        WishEntity expected = new WishEntity(member, product);
        WishEntity actual = wishRepository.save(expected);
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getMember()).isEqualTo(expected.getMember())
        );
    }

    @Test
    void findByName() {
        MemberEntity member = new MemberEntity("a","b", Role.CONSUMER);
        ProductEntity product = new ProductEntity(categoryRepository.findById(1L).get(),"a",1,"b");
        memberRepository.save(member);
        productRepository.save(product);

        wishRepository.save(new WishEntity(member, product));
        WishEntity actual = wishRepository.findByMemberId(member.getId()).getFirst();

        assertThat(actual.getMember()).isEqualTo(member);
    }

    @Test
    void deleteByProductIDAndMemberId(){
        MemberEntity member = new MemberEntity("a","b", Role.CONSUMER);
        ProductEntity product = new ProductEntity(categoryRepository.findById(1L).get(),"a",1,"b");
        memberRepository.save(member);
        productRepository.save(product);
        wishRepository.save(new WishEntity(member, product));
        MemberEntity actual = wishRepository.findByMemberIdAndProductId(member.getId(), product.getId()).getMember();
        assertThat(actual).isEqualTo(member);
    }
}
