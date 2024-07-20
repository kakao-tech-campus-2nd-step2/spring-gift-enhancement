package test;


import gift.repository.WishRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class WishRepositoryTest {
    private WishRepository wishes;

    public WishRepositoryTest() {
    }

    @Test
    public void save() {
    }

}
